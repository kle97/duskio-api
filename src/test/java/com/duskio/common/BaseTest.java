package com.duskio.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    
    protected SoftAssertJ softly() {
        return SoftAssertJ.getInstance();
    }

    @BeforeEach
    public void baseTestBeforeEach(TestInfo testInfo) {
        LoggerFactory.getLogger(getClass()).info(testInfo.getDisplayName());
    }

    @AfterEach
    public void baseTestAfterEach() {
        softly().assertAll();
    }
}
