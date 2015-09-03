package com.google.checkstyle.test.chapter4formatting.rule451wheretobreack;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck;

public class MethodParamPadTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void operatorWrapTest() throws Exception {
        
        Class<MethodParamPadCheck> clazz = MethodParamPadCheck.class;
        String messageKey = "line.previous";

        final String[] expected = {
            "83:9: " + getCheckMessage(clazz, messageKey, "("),
            "128:13: " + getCheckMessage(clazz, messageKey, "("),
            "130:9: " + getCheckMessage(clazz, messageKey, "("),
        };
        Configuration checkConfig = builder.getCheckConfig("MethodParamPad");
        String filePath = builder.getFilePath("MethodParamPadInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}