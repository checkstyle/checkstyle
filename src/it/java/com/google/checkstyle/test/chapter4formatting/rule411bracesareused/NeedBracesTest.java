package com.google.checkstyle.test.chapter4formatting.rule411bracesareused;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class NeedBracesTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void needBracesTest() throws IOException, Exception {
        
        Class<NeedBracesCheck> clazz = NeedBracesCheck.class;
        String messageKey = "needBraces";

        final String[] expected = {
            "29: " + getCheckMessage(clazz, messageKey, "do"),
            "41: " + getCheckMessage(clazz, messageKey, "while"),
            "42: " + getCheckMessage(clazz, messageKey, "while"),
            "44: " + getCheckMessage(clazz, messageKey, "while"),
            "45: " + getCheckMessage(clazz, messageKey, "if"),
            "58: " + getCheckMessage(clazz, messageKey, "for"),
            "59: " + getCheckMessage(clazz, messageKey, "for"),
            "61: " + getCheckMessage(clazz, messageKey, "for"),
            "63: " + getCheckMessage(clazz, messageKey, "if"),
            "82: " + getCheckMessage(clazz, messageKey, "if"),
            "83: " + getCheckMessage(clazz, messageKey, "if"),
            "85: " + getCheckMessage(clazz, messageKey, "if"),
            "87: " + getCheckMessage(clazz, messageKey, "else"),
            "89: " + getCheckMessage(clazz, messageKey, "if"),
            "97: " + getCheckMessage(clazz, messageKey, "else"),
            "99: " + getCheckMessage(clazz, messageKey, "if"),
            "100: " + getCheckMessage(clazz, messageKey, "if"),
            "126: " + getCheckMessage(clazz, messageKey, "while"),
            "129: " + getCheckMessage(clazz, messageKey, "do"),
            "135: " + getCheckMessage(clazz, messageKey, "if"),
            "138: " + getCheckMessage(clazz, messageKey, "if"),
            "139: " + getCheckMessage(clazz, messageKey, "else"),
            "144: " + getCheckMessage(clazz, messageKey, "for"),
            "147: " + getCheckMessage(clazz, messageKey, "for"),
            "157: " + getCheckMessage(clazz, messageKey, "while"),
            "160: " + getCheckMessage(clazz, messageKey, "do"),
            "166: " + getCheckMessage(clazz, messageKey, "if"),
            "169: " + getCheckMessage(clazz, messageKey, "if"),
            "170: " + getCheckMessage(clazz, messageKey, "else"),
            "175: " + getCheckMessage(clazz, messageKey, "for"),
            "178: " + getCheckMessage(clazz, messageKey, "for"),
            "189: " + getCheckMessage(clazz, messageKey, "while"),
            "192: " + getCheckMessage(clazz, messageKey, "do"),
            "198: " + getCheckMessage(clazz, messageKey, "if"),
            "201: " + getCheckMessage(clazz, messageKey, "if"),
            "202: " + getCheckMessage(clazz, messageKey, "else"),
            "207: " + getCheckMessage(clazz, messageKey, "for"),
            "210: " + getCheckMessage(clazz, messageKey, "for"),
        };

        Configuration checkConfig = builder.getCheckConfig("NeedBraces");
        String filePath = builder.getFilePath("NeedBracesInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
