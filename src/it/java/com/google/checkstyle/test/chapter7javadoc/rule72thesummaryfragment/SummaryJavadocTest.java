package com.google.checkstyle.test.chapter7javadoc.rule72thesummaryfragment;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck;

public class SummaryJavadocTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void testCorrect() throws Exception {
        
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("SummaryJavadocCheck");
        String filePath = builder.getFilePath("InputCorrectSummaryJavaDocCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect() throws Exception {
        
        String msg_first_sentence = getCheckMessage(SummaryJavadocCheck.class, "summary.first.sentence");
        String msg_forbidden_fragment = getCheckMessage(SummaryJavadocCheck.class, "summary.javaDoc");

        final String[] expected = {
            "14: " + msg_first_sentence,
            "37: " + msg_first_sentence,
            "47: " + msg_forbidden_fragment,
            "58: " + msg_forbidden_fragment,
            "69: " + msg_first_sentence,
            "83: " + msg_forbidden_fragment,
            "103: " + msg_first_sentence,
        };

        Configuration checkConfig = builder.getCheckConfig("SummaryJavadocCheck");
        String filePath = builder.getFilePath("InputIncorrectSummaryJavaDocCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
