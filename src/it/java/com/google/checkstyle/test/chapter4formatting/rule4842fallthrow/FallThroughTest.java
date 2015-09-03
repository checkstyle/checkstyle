package com.google.checkstyle.test.chapter4formatting.rule4842fallthrow;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck;

public class FallThroughTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void fallThroughTest() throws Exception {

        String msg = getCheckMessage(FallThroughCheck.class, "fall.through");
                     
        final String[] expected = {
            "14:13: " + msg,
            "38:13: " + msg, 
            "47:13: " + msg,
            "53:13: " + msg,
            "70:13: " + msg,
            "87:13: " + msg,
            "105:13: " + msg,
            "123:13: " + msg,
            "179:11: " + msg,
            "369:11: " + msg,
            "372:11: " + msg,
            "374:41: " + msg,
        };
        
        Configuration checkConfig = builder.getCheckConfig("FallThrough");
        String filePath = builder.getFilePath("FallThroughInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
