package com.google.checkstyle.test.chapter2filebasic.rule233nonascii;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck;

public class AvoidEscapedUnicodeCharactersCheckTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void unicodeEscapesTest() throws IOException, Exception {

        String msg = getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, "forbid.escaped.unicode.char");

        final String[] expected = {
            "5: " + msg,
            "15: " + msg,
            "25: " + msg,
            "33: " + msg,
            "35: " + msg,
            "36: " + msg,
         };

        Configuration checkConfig = builder.getCheckConfig("AvoidEscapedUnicodeCharacters");
        String filePath = builder.getFilePath("AvoidEscapedUnicodeCharactersInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


