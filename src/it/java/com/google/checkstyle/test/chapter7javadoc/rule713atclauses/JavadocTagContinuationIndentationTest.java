package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class JavadocTagContinuationIndentationTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void noFinalizerBasicTest() throws IOException, Exception {
        
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
    public void noFinalizerExtendedTest() throws IOException, Exception {
        
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


