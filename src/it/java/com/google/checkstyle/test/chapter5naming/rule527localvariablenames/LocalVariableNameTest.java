package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class LocalVariableNameTest extends BaseCheckTestSupport{

	private static final String MSG_KEY = "name.invalidPattern";
	private static ConfigurationBuilder builder;
	private static Configuration checkConfig;
	private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("LocalVariableName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void localVariableNameTest() throws Exception {

        final String[] expected = {
            "26:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "a", format),
            "27:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aA", format),
            "28:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "a1_a", format),
            "29:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "A_A", format),
            "30:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aa2_a", format),
            "31:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_a", format),
            "32:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_aa", format),
            "33:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aa_", format),
            "34:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aaa$aaa", format),
            "35:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$aaaaaa", format),
            "36:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aaaaaa$", format),
        };

        String filePath = builder.getFilePath("LocalVariableNameInput_Simple");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void oneCharTest() throws Exception {

        final String[] expected = {
            "15:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "i", format),
            "21:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "I_ndex", format),
            "45:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "i_ndex", format),
            "49:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "ii_i1", format),
            "53:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$index", format),
            "57:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "in$dex", format),
            "61:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "index$", format),
        };

        String filePath = builder.getFilePath("LocalVariableNameInput_OneCharVarName");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
