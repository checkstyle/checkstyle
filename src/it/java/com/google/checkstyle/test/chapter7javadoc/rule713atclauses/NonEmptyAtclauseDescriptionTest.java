package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;


import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck;

public class NonEmptyAtclauseDescriptionTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        String msg = getCheckMessage(NonEmptyAtclauseDescriptionCheck.class, "non.empty.atclause");

        final String[] expected = {
            "34: " + msg,
            "35: " + msg,
            "36: " + msg,
            "37: " + msg,
            "38: " + msg,
            "39: " + msg,
            "48: " + msg,
            "49: " + msg,
            "50: " + msg,
            "51: " + msg,
            "52: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("NonEmptyAtclauseDescription");
        String filePath = builder.getFilePath("InputNonEmptyAtclauseDescriptionCheck");
        
        Integer[] warnList = getLineNumbersFromExpected(expected);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSpaceSequence() throws Exception {
        String msg = getCheckMessage(NonEmptyAtclauseDescriptionCheck.class, "non.empty.atclause");

        final String[] expected = {
            "27: " + msg,
            "28: " + msg,
            "29: " + msg,
            "38: " + msg,
            "39: " + msg,
            "40: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("NonEmptyAtclauseDescription");
        String filePath = builder.getFilePath("InputNonEmptyAtclauseDescriptionCheckSpaceSeq");
        
        Integer[] warnList = getLineNumbersFromExpected(expected);
        verify(checkConfig, filePath, expected, warnList);
    }

    /**
     * Gets line numbers with violations from an array with expected messages.
     * This is used as using "warn" comments in input files would affects the work
     * of the Check.
     * @param expected an array with expected messages.
     * @return Integer array with numbers of lines with violations.
     */
    private static Integer[] getLineNumbersFromExpected(String[] expected) {
        Integer[] result = new Integer[expected.length];
        for (int i = 0; i < expected.length; i++) {
            result[i] = Integer.valueOf(expected[i].substring(0, expected[i].indexOf(':')));
        }
        return result;
    }
}
