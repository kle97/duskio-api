package com.duskio.common;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    
    protected SoftAssertJ softAssert() {
        return SoftAssertJ.getInstance();
    }
}
