package com.rj.service.impl;

import com.rj.pojo.Submit;
import com.rj.service.ServiceFilter;
import org.springframework.stereotype.Service;

@Service
public class BlackPhoneFilter implements ServiceFilter {
    @Override
    public Submit excute(Submit submit) {
        System.err.println("执行黑名单处理业务");
        return submit;
    }
}
