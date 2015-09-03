package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class AbbreviationAsWordInNameTest extends BaseCheckTestSupport{

    private static final String MSG_KEY = "abbreviation.as.word";
    private static ConfigurationBuilder builder;
    private final Class<AbbreviationAsWordInNameCheck> clazz = AbbreviationAsWordInNameCheck.class;
    private static Configuration checkConfig;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("AbbreviationAsWordInName");
    }

    @Test
    public void abbreviationAsWordInNameTest() throws Exception {

        int maxCapitalCount = 1;
        String msg = getCheckMessage(clazz, MSG_KEY, maxCapitalCount);

        final String[] expected = {
            "50: " + msg,
            "52: " + msg,
            "54: " + msg,
            "58: " + msg,
            "60: " + msg,
            "62: " + msg,
            "67: " + msg,
            "69: " + msg,
            "71: " + msg,
        };

        String filePath = builder.getFilePath("InputAbbreviationAsWordInTypeNameCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}