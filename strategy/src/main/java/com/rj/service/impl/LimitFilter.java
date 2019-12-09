package com.rj.service.impl;

import com.rj.constans.MyConstants;
import com.rj.feign.MyFeign;
import com.rj.pojo.Submit;
import com.rj.service.ServiceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 限流过滤器，我们的原则是5分钟内，同样的客户ID不能重复发送三条；1小时内不能重复发送10条
 * 解决办法：
 * 我们可以在redis中使用String类型，并设置过期时间，key是客户Id，value是次数，在这个时间内，每有发送信息，次数就+1
 * 判断超过相关次数就过滤失败，也就是说，我们需要放入redis中两个常量，一个是5分钟的，一个是1小时的
 */
@Service
public class LimitFilter implements ServiceFilter {
    @Autowired
    private MyFeign myFeign;

    @Value("${send_limit.five_minute}")
    private int minute_count;

    @Value("${send_limit.hour}")
    private int hour_count;

    @Override
    public Submit excute(Submit submit) {
        String five_minute_key = MyConstants.LIMIT_FIVE_MINUTE + submit.getClientId();
        String hour_key = MyConstants.LIMIT_HOUR + submit.getClientId();

        boolean five = checkLimit(minute_count, five_minute_key, submit, 5*60);
        boolean hour = checkLimit(hour_count, hour_key, submit, 60*60);

        //当两种情况都满足的时候
        if (five && hour) {
            return submit;
        }

        submit.setContent("内容有敏感词，或者被限流了，请5分钟后重试");
        return submit;
    }

    //判断是否限流方法
    public boolean checkLimit(int count, String key, Submit submit, int expireTime) {
        String value = myFeign.getValue(key);

        if (value != null && !value.equals("null")) {
            Long result = Long.valueOf(value);
            if (result < count) {
                myFeign.incr(key, 1);
                return true;
            } else {
                return false;
            }
        }
        myFeign.set(key, 1, expireTime);
        return true;
    }
}
