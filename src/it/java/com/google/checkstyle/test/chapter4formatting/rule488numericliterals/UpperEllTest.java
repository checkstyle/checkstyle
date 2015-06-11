package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class UpperEllTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void upperEllTest() throws IOException, Exception {
        
        final String[] expected = {
            "6:36: Should use uppercase 'L'.",
            "12:27: Should use uppercase 'L'.",
            "14:32: Should use uppercase 'L'.",
            "17:19: Should use uppercase 'L'.",
            "21:29: Should use uppercase 'L'.",
            "22:22: Should use uppercase 'L'.",
            "25:15: Should use uppercase 'L'.",
            "34:47: Should use uppercase 'L'.",
            "40:31: Should use uppercase 'L'.",
            "42:36: Should use uppercase 'L'.",
            "45:23: Should use uppercase 'L'.",
            "50:33: Should use uppercase 'L'.",
            "51:26: Should use uppercase 'L'.",
            "56:23: Should use uppercase 'L'.",
            "65:48: Should use uppercase 'L'.",
            "71:39: Should use uppercase 'L'.",
            "73:44: Should use uppercase 'L'.",
            "76:31: Should use uppercase 'L'.",
            "80:41: Should use uppercase 'L'.",
            "81:34: Should use uppercase 'L'.",
            "84:27: Should use uppercase 'L'.",
            "97:46: Should use uppercase 'L'.",
            "99:29: Should use uppercase 'L'.",
            "100:22: Should use uppercase 'L'.",
        };
        
        Configuration checkConfig = builder.getCheckConfig("UpperEll");
        String filePath = builder.getFilePath("UpperEllInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


