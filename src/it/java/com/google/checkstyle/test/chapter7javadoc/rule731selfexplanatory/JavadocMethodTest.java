package com.google.checkstyle.test.chapter7javadoc.rule731selfexplanatory;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;

public class JavadocMethodTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void javadocMethodTest() throws Exception {

        String msg = getCheckMessage(JavadocMethodCheck.class, "javadoc.missing");

        final String[] expected = {
            "57:5: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("JavadocMethod");
        String filePath = builder.getFilePath("InputJavadocMethodCheck");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
