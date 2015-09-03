package com.google.checkstyle.test.chapter3filestructure.rule331nowildcard;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class AvoidStarImportTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void starImportTest() throws Exception {

        final String[] expected = {
            "3: Using the '.*' form of import should be avoided - java.io.*.",
            "4: Using the '.*' form of import should be avoided - java.lang.*.",
            "18: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
            "19: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
        };
        
        Configuration checkConfig = builder.getCheckConfig("AvoidStarImport");
        String filePath = builder.getFilePath("AvoidStarImportInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
