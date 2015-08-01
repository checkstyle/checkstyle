package com.google.checkstyle.test.chapter6programpractice.rule62donotignoreexceptions;

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
