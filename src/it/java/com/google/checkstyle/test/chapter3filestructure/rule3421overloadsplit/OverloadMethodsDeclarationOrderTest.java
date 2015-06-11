package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck;

public class OverloadMethodsDeclarationOrderTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void overloadMethodsTest() throws IOException, Exception {
        
        Class<OverloadMethodsDeclarationOrderCheck> clazz = OverloadMethodsDeclarationOrderCheck.class;
        String messageKey = "overload.methods.declaration";

        final String[] expected = {
            "26: " + getCheckMessage(clazz, messageKey, 15),
            "54: " + getCheckMessage(clazz, messageKey, 43),
            "66: " + getCheckMessage(clazz, messageKey, 64),
            "109: " + getCheckMessage(clazz, messageKey, 98),
        };

        Configuration checkConfig = builder.getCheckConfig("OverloadMethodsDeclarationOrder");
        String filePath = builder.getFilePath("InputOverloadMethodsDeclarationOrder");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


