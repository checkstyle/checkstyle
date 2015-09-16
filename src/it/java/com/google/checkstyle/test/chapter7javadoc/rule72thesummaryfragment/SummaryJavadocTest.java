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
        
        String msgFirstSentence = getCheckMessage(SummaryJavadocCheck.class, "summary.first.sentence");
        String msgForbiddenFragment = getCheckMessage(SummaryJavadocCheck.class, "summary.javaDoc");

        final String[] expected = {
            "14: " + msgFirstSentence,
            "37: " + msgFirstSentence,
            "47: " + msgForbiddenFragment,
            "58: " + msgForbiddenFragment,
            "69: " + msgFirstSentence,
            "83: " + msgForbiddenFragment,
            "103: " + msgFirstSentence,
        };

        Configuration checkConfig = builder.getCheckConfig("SummaryJavadocCheck");
        String filePath = builder.getFilePath("InputIncorrectSummaryJavaDocCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
