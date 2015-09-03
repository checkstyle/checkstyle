package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;

public class NoFinalizerTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void noFinalizerBasicTest() throws Exception {
        
        String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "5: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("NoFinalizer");
        String filePath = builder.getFilePath("NoFinalizerInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void noFinalizerExtendedTest() throws Exception {
        
        String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "9: " + msg,
            "21: " + msg,
            "33: " + msg,
            "45: " + msg,
            "57: " + msg,
            "69: " + msg,
            "79: " + msg,
            "119: " + msg,
            "136: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("NoFinalizer");
        String filePath = builder.getFilePath("NoFinalizeExtendInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
