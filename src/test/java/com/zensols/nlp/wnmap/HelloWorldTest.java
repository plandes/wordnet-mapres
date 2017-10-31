package com.zensols.nlp.wnmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloWorldTest {
    private static final Log log = LogFactory.getLog(HelloWorldTest.class);

    @BeforeClass
    public static void setup() {
	if (log.isDebugEnabled()) {
	    log.debug("setting up...");
	}
    }

    @AfterClass
    public static void tearDown() {
	if (log.isDebugEnabled()) {
	    log.debug("tearing down...");
	}
    }

    @Test
    public void testHelloWorld() throws Exception {
        Assert.assertEquals(1, 1);
    }
}
