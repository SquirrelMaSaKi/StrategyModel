package com.rj.service.impl;

import com.rj.pojo.Submit;
import com.rj.service.ServiceFilter;
import org.springframework.stereotype.Service;

@Service
public class FeeFilter implements ServiceFilter {
    @Override
    public Submit excute(Submit submit) {
        System.err.println("执行手机话费相关业务");
        return submit;
    }
}
