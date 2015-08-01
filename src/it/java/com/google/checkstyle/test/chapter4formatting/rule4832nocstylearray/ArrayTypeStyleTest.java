package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck;

public class ArrayTypeStyleTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void arrayTypeStyleTest() throws IOException, Exception {
        
        String msg = getCheckMessage(ArrayTypeStyleCheck.class, "array.type.style");

        final String[] expected = {
            "9:23: " + msg,
            "15:44: " + msg,
            "21:20: " + msg,
            "22:23: " + msg,
            "41:16: " + msg,
            "42:19: " + msg,
        };
        
        Configuration checkConfig = builder.getCheckConfig("ArrayTypeStyle");
        String filePath = builder.getFilePath("ArrayTypeStyleInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
