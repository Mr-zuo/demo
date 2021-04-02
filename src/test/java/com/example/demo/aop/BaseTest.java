package com.example.demo.aop;

import com.example.demo.DemoApplication;
import com.example.demo.aop.invalid.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/1
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class BaseTest {

    @Autowired
    AsyncService asyncService;

    @Test
    public void testAsync() {
        asyncService.async1();
        asyncService.async2();
    }
}
