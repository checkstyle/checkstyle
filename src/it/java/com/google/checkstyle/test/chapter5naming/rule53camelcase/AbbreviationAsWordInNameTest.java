package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class AbbreviationAsWordInNameTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;
    private Class<AbbreviationAsWordInNameCheck> clazz = AbbreviationAsWordInNameCheck.class;
    private String msgKey = "abbreviation.as.word";
    private static Configuration checkConfig;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("AbbreviationAsWordInName");
    }

    @Test
    public void abbreviationAsWordInNameTest() throws IOException, Exception {

        int maxCapitalCount = 1;
        String msg = getCheckMessage(clazz, msgKey, maxCapitalCount);

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