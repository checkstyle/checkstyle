package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck;

public class EmptyBlockTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void emptyBlockTest() throws IOException, Exception {
        
        final String[] expected = {
            "19:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "22:34: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "26:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "28:20: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "68:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "71:38: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "75:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "77:24: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "98:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "101:42: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "105:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "107:28: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "126:16: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "172:28: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "173:14: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "175:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "179:14: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "181:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "182:26: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "195:20: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "241:32: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "242:18: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "244:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "248:18: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "250:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "251:30: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "264:24: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "310:36: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "311:22: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "313:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "317:22: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "319:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "320:34: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyBlock");
        String filePath = builder.getFilePath("EmptyBlockInput_Basic");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void emptyBlockTestCatch() throws IOException, Exception {
        
        final String[] expected = {
            "29:17: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
            "50:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
            "72:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyBlock");
        String filePath = builder.getFilePath("EmptyBlockInputCatch");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


