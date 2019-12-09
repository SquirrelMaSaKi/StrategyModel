package com.rj.service.impl;

import com.rj.constans.MyConstants;
import com.rj.feign.MyFeign;
import com.rj.pojo.Submit;
import com.rj.service.ServiceFilter;
import com.rj.utils.AnalyzerUtil;
import com.rj.utils.IKAnalyzer4Lucene7;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DirtyWordFilter implements ServiceFilter {
    @Autowired
    private MyFeign myFeign;

    @Override
    public Submit excute(Submit submit) {
        String content = submit.getContent();

        //创建分词器
        Analyzer ik = new IKAnalyzer4Lucene7();

        //创建list集合，接收分词结果
        List list = null;

        try {
            TokenStream tokenStream = ik.tokenStream("content", content);
            list = AnalyzerUtil.doToken(tokenStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < list.size(); i++) {
            String value = myFeign.getValue(MyConstants.DIRTYWORD + list.get(i));
            if (value != null && !value.equals("null")) {
                //有脏词，打印，一般我们是要打印到日志
                System.err.println("内容有脏词：" + list.get(i));
                //并将content设置为有脏词，用户最后下发队列时候的判断
                submit.setContent("内容有敏感词，或者被限流了，请5分钟后重试");
                return submit;
            }
        }
        //消息合格，将对象就直接返回出去就行了，在servlet中我们可以将其放入队列，所以我们要动态创建队列
        return submit;
    }
}
