package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck;

public class SingleLineJavadocTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void singleLineJavadocTest() throws Exception {
        
        String msg = getCheckMessage(SingleLineJavadocCheck.class, "singleline.javadoc");

        final String[] expected = {
            "5: " + msg,
            "13: " + msg,
            "29: " + msg,
            "32: " + msg,
            "35: " + msg,
            "38: " + msg,
            "41: " + msg,
        };

        final DefaultConfiguration checkConfig = createCheckConfig(SingleLineJavadocCheck.class);
        checkConfig.addAttribute("ignoreInlineTags", "false");
        String filePath = builder.getFilePath("InputSingleLineJavadocCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test(expected = Exception.class)
    public void customInlineTagTest() throws Exception{
        String msg = getCheckMessage(SingleLineJavadocCheck.class, "singleline.javadoc");
        
        Configuration checkConfig = builder.getCheckConfig("SingleLineJavadocCheck");
        String filePath = builder.getFilePath("InputSingleLineJavadocCheckError");

        final String[] expected = {
            "4: " + msg,
        };

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
