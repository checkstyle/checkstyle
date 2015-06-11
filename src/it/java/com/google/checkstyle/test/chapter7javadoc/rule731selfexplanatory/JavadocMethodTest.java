package com.google.checkstyle.test.chapter7javadoc.rule731selfexplanatory;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class JavadocMethodTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void javadocMethodTest() throws IOException, Exception {

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


