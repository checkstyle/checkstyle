package com.google.checkstyle.test.chapter4formatting.rule44cloumunlimit;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;

public class LineLengthTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void lineLengthTest() throws Exception {
        
        final String[] expected = {
             "5: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 112),
             "29: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 183),
             "46: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 131),
             "47: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 124),
             "48: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 113),
             "50: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 116),
             "53: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 131),
             "57: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", 100, 116),
        };

        Configuration checkConfig = builder.getCheckConfig("LineLength");
        String filePath = builder.getFilePath("LineLengthInput2");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
