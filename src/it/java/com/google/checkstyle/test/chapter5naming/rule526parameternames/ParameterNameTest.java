package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ParameterNameTest extends BaseCheckTestSupport{

	private static final String MSG_KEY = "name.invalidPattern";
	private static String format;
	private static ConfigurationBuilder builder;
	private static Configuration checkConfig;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("ParameterName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void parameterNameTest() throws Exception {

        final String[] expected = {
            "8:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$arg1", format),
            "9:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "ar$g2", format),
            "10:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "arg3$", format),
            "11:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "a_rg4", format),
            "12:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_arg5", format),
            "13:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "arg6_", format),
            "14:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aArg7", format),
            "15:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aArg8", format),
            "16:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aar_g", format),
            "26:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bB", format),
            "49:22: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "llll_llll", format),
            "50:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bB", format),
        };

        String filePath = builder.getFilePath("ParameterNameInput_Simple");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
