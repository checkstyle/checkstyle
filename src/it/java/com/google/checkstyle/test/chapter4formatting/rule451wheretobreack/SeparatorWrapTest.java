package com.google.checkstyle.test.chapter4formatting.rule451wheretobreack;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck;

public class SeparatorWrapTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void separatorWrapTest() throws Exception {

        final String[] expected = {
            "28:30: " + getCheckMessage(SeparatorWrapCheck.class, "line.new", "."),
        };

        Configuration checkConfig = builder.getCheckConfig("SeparatorWrap");
        String filePath = builder.getFilePath("SeparatorWrapInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
