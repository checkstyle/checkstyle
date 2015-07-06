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
            "58:21: " + msg,
            "60:21: " + msg,
            "62:42: " + msg,
            "65:25: " + msg,
            "66:35: " + msg,
            "76:14: " + msg,
            "103:25: " + msg,
            "105:25: " + msg,
            "107:46: " + msg,
            "110:29: " + msg,
            "111:39: " + msg,
            "119:15: " + msg,
            "131:23: " + msg,
            "146:59: " + msg,
            "155:4: " + msg,
            "186:19: " + msg,
            "204:15: " + msg,
            "212:15: " + msg,
            "224:6: " + msg,
            "233:22: " + msg,
            "323:39: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("OneStatementPerLine");
        String filePath = builder.getFilePath("OneStatementPerLineInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
