package com.duskio.common;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseSpringTest extends AbstractTestNGSpringContextTests {

    protected SoftAssertJ2 softly() {
        return SoftAssertJ2.getInstance();
    }

    @BeforeMethod
    public void baseTestBeforeMethod(Method method) {
        LoggerFactory.getLogger(getClass()).info(method.getName());
    }

    @AfterMethod
    public void baseTestAfterMethod() {
        softly().assertAll();
    }
}
