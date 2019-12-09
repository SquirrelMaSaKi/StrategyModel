package com.rj;

import com.rj.constans.MyConstants;
import com.rj.feign.MyFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = StrategyStartApp.class)
public class StrategyStartTest {
    @Autowired
    private MyFeign myFeign;

    @Test
    public void testFeign() {
        List<String> list = myFeign.getList(MyConstants.FILTERLIST);
        for (String s : list) {
            System.err.println(s);
        }
    }
}
