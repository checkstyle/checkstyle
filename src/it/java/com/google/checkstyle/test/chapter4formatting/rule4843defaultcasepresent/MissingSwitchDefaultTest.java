package com.google.checkstyle.test.chapter4formatting.rule4843defaultcasepresent;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck;

public class MissingSwitchDefaultTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void missingSwitchDefaultTest() throws Exception {
        
        String msg = getCheckMessage(MissingSwitchDefaultCheck.class, "missing.switch.default");

        final String[] expected = {
            "11:9: " + msg,
            "19:9: " + msg,
            "23:9: " + msg,
            "31:13: " + msg,
            "38:21: " + msg,
            "42:21: " + msg,
        };
        
        Configuration checkConfig = builder.getCheckConfig("MissingSwitchDefault");
        String filePath = builder.getFilePath("MissingSwitchDefaultInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
