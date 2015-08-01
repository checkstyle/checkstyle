package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class IllegalTokenTextTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder()
    		throws CheckstyleException, MalformedURLException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void illegalTokensTest() throws IOException, Exception {
        
        final String[] expected = {
            "17:27: Avoid using corresponding octal or Unicode escape.",
            "18:27: Avoid using corresponding octal or Unicode escape.",
            "19:27: Avoid using corresponding octal or Unicode escape.",
            "22:25: Avoid using corresponding octal or Unicode escape.",
            "23:25: Avoid using corresponding octal or Unicode escape.",
            "25:16: Avoid using corresponding octal or Unicode escape.",
            "42:21: Avoid using corresponding octal or Unicode escape.",
            "43:21: Avoid using corresponding octal or Unicode escape.",
            "44:21: Avoid using corresponding octal or Unicode escape.",
            "45:21: Avoid using corresponding octal or Unicode escape.",
            "46:21: Avoid using corresponding octal or Unicode escape.",
            "47:21: Avoid using corresponding octal or Unicode escape.",
            "48:21: Avoid using corresponding octal or Unicode escape.",
            "49:21: Avoid using corresponding octal or Unicode escape.",
            "54:21: Avoid using corresponding octal or Unicode escape.",
            "55:21: Avoid using corresponding octal or Unicode escape.",
            "56:21: Avoid using corresponding octal or Unicode escape.",
            "57:21: Avoid using corresponding octal or Unicode escape.",
            "58:21: Avoid using corresponding octal or Unicode escape.",
            "59:21: Avoid using corresponding octal or Unicode escape.",
            "60:21: Avoid using corresponding octal or Unicode escape.",
            "61:21: Avoid using corresponding octal or Unicode escape.",
            "68:31: Avoid using corresponding octal or Unicode escape.",
            "69:31: Avoid using corresponding octal or Unicode escape.",
            "70:31: Avoid using corresponding octal or Unicode escape.",
            "73:29: Avoid using corresponding octal or Unicode escape.",
            "74:29: Avoid using corresponding octal or Unicode escape.",
            "76:20: Avoid using corresponding octal or Unicode escape.",
            "93:25: Avoid using corresponding octal or Unicode escape.",
            "94:25: Avoid using corresponding octal or Unicode escape.",
            "95:25: Avoid using corresponding octal or Unicode escape.",
            "96:25: Avoid using corresponding octal or Unicode escape.",
            "97:25: Avoid using corresponding octal or Unicode escape.",
            "98:25: Avoid using corresponding octal or Unicode escape.",
            "99:25: Avoid using corresponding octal or Unicode escape.",
            "100:25: Avoid using corresponding octal or Unicode escape.",
            "105:25: Avoid using corresponding octal or Unicode escape.",
            "106:25: Avoid using corresponding octal or Unicode escape.",
            "107:25: Avoid using corresponding octal or Unicode escape.",
            "108:25: Avoid using corresponding octal or Unicode escape.",
            "109:25: Avoid using corresponding octal or Unicode escape.",
            "110:25: Avoid using corresponding octal or Unicode escape.",
            "111:25: Avoid using corresponding octal or Unicode escape.",
            "112:25: Avoid using corresponding octal or Unicode escape.",
            "118:35: Avoid using corresponding octal or Unicode escape.",
            "119:35: Avoid using corresponding octal or Unicode escape.",
            "120:35: Avoid using corresponding octal or Unicode escape.",
            "123:33: Avoid using corresponding octal or Unicode escape.",
            "124:33: Avoid using corresponding octal or Unicode escape.",
            "126:24: Avoid using corresponding octal or Unicode escape.",
            "143:29: Avoid using corresponding octal or Unicode escape.",
            "144:29: Avoid using corresponding octal or Unicode escape.",
            "145:29: Avoid using corresponding octal or Unicode escape.",
            "146:29: Avoid using corresponding octal or Unicode escape.",
            "147:29: Avoid using corresponding octal or Unicode escape.",
            "148:29: Avoid using corresponding octal or Unicode escape.",
            "149:29: Avoid using corresponding octal or Unicode escape.",
            "150:29: Avoid using corresponding octal or Unicode escape.",
            "155:29: Avoid using corresponding octal or Unicode escape.",
            "156:29: Avoid using corresponding octal or Unicode escape.",
            "157:29: Avoid using corresponding octal or Unicode escape.",
            "158:29: Avoid using corresponding octal or Unicode escape.",
            "159:29: Avoid using corresponding octal or Unicode escape.",
            "160:29: Avoid using corresponding octal or Unicode escape.",
            "161:29: Avoid using corresponding octal or Unicode escape.",
            "162:29: Avoid using corresponding octal or Unicode escape.",
        };

        Configuration checkConfig = builder.getCheckConfig("IllegalTokenText");
        String filePath = builder.getFilePath("IllegalTokenTextInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
