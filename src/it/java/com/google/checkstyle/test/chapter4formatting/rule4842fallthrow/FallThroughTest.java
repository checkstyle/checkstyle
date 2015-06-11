package com.google.checkstyle.test.chapter4formatting.rule4842fallthrow;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class FallThroughTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void fallThroughTest() throws IOException, Exception {

        String msg = getCheckMessage(FallThroughCheck.class, "fall.through");
                     
        final String[] expected = {
            "14:13: " + msg,
            "38:13: " + msg, 
            "53:13: " + msg,
            "70:13: " + msg,
            "87:13: " + msg,
            "105:13: " + msg,
            "123:13: " + msg,
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


