package com.jtissdev;

import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test for simple App.
 */
@DisplayName("App Test Suite")
@ExtendWith(TestResultLogger.class)
public class AppTest
{
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);
    @Test
    void testApp() {
        assertTrue(true);
    }

}
