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
    public void oneStatementTest() throws Exception {

        String msg = getCheckMessage(OneStatementPerLineCheck.class, "multiple.statements.line");

        final String[] expected = {
            "6:59: " + msg,
            "50:21: " + msg,
            "52:21: " + msg,
            "54:42: " + msg,
            "57:25: " + msg,
            "58:35: " + msg,
            "68:14: " + msg,
            "95:25: " + msg,
            "97:25: " + msg,
            "99:46: " + msg,
            "102:29: " + msg,
            "103:39: " + msg,
            "111:15: " + msg,
            "123:23: " + msg,
            "138:59: " + msg,
            "170:19: " + msg,
            "188:15: " + msg,
            "196:15: " + msg,
            "208:6: " + msg,
            "217:22: " + msg,
            "307:39: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("OneStatementPerLine");
        String filePath = builder.getFilePath("OneStatementPerLineInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {

        String msg = getCheckMessage(OneStatementPerLineCheck.class, "multiple.statements.line");

        final String[] expected = {
            "24:6: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("OneStatementPerLine");
        String filePath = new File("src/test/resources-noncompilable/"
            + "com/puppycrawl/tools/checkstyle/coding/"
            + "InputOneStatementPerLineCheck.java").getCanonicalPath();

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
