package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck;
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class SingleLineJavadocTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void singleLineJavadocTest() throws IOException, Exception {
        
        String msg = getCheckMessage(SingleLineJavadocCheck.class, "singleline.javadoc");

        final String[] expected = {
            "5: " + msg,
            "13: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("SingleLineJavadoc");
        String filePath = builder.getFilePath("InputSingleLineJavadocCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


