package com.google.checkstyle.test.chapter4formatting.rule4821onevariablepreline;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class MultipleVariableDeclarationsTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void multipleVariableDeclarationsTest() throws IOException, Exception {
        
        String msgComma = getCheckMessage(MultipleVariableDeclarationsCheck.class, "multiple.variable.declarations.comma");
        String msg = getCheckMessage(MultipleVariableDeclarationsCheck.class, "multiple.variable.declarations");

        final String[] expected = {
            "5:5: " + msgComma,
            "6:5: " + msg,
            "9:9: " + msgComma,
            "10:9: " + msg,
            "14:5: " + msg,
            "17:5: " + msg,
            "31:9: " + msgComma,
            "32:9: " + msg,
            "35:13: " + msgComma,
            "36:13: " + msg,
            "40:9: " + msg,
            "43:9: " + msg,
            "57:13: " + msgComma,
            "58:13: " + msg,
            "61:17: " + msgComma,
            "62:17: " + msg,
            "66:13: " + msg,
            "69:13: " + msg,
            "86:5: " + msgComma,
            "89:5: " + msgComma,
        };

        Configuration checkConfig = builder.getCheckConfig("MultipleVariableDeclarations");
        String filePath = builder.getFilePath("MultipleVariableDeclarationsInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


