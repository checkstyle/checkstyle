package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck;

public class OneStatementPerLineTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void oneStatmentTest() throws IOException, Exception {
        
        String msg = getCheckMessage(OneStatementPerLineCheck.class, "multiple.statements.line");

        final String[] expected = {
            "6:59: " + msg,
            "58:25: " + msg,
            "60:25: " + msg,
            "62:46: " + msg,
            "65:29: " + msg,
            "66:39: " + msg,
            "67:62: " + msg,
            "76:18: " + msg,
            "103:29: " + msg,
            "105:29: " + msg,
            "107:50: " + msg,
            "110:33: " + msg,
            "111:43: " + msg,
            "112:66: " + msg,
            "119:17: " + msg,
            "131:25: " + msg,
            "146:63: " + msg,
            "155:6: " + msg,
            "186:21: " + msg,
            "204:17: " + msg,
            "212:25: " + msg,
            "224:10: " + msg,
            "233:24: " + msg,
        };
        
        Configuration checkConfig = builder.getCheckConfig("OneStatementPerLine");
        String filePath = builder.getFilePath("OneStatementPerLineInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


