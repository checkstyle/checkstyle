package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ClassMethodTypeParameterNameTest extends BaseCheckTestSupport{

    private static final String MSG_KEY = "name.invalidPattern";
    private static ConfigurationBuilder builder;
    private static Configuration configuration;
    private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        configuration = builder.getCheckConfig("ClassTypeParameterName");
        format = configuration.getAttribute("format");
    }

    @Test
    public void testClassDefault() throws Exception {

        final String[] expected = {
            "5:31: " + getCheckMessage(configuration.getMessages(), MSG_KEY, "t", format),
            "13:14: " + getCheckMessage(configuration.getMessages(), MSG_KEY, "foo", format),
            "27:24: " + getCheckMessage(configuration.getMessages(), MSG_KEY, "$foo", format),
        };

        String filePath = builder.getFilePath("ClassTypeParameterNameInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(configuration, filePath, expected, warnList);
    }

    @Test
    public void testMethodDefault() throws Exception {

        Configuration checkConfig = builder.getCheckConfig("MethodTypeParameterName");

        final String[] expected = {
            "9:6: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "e_e", format),
            "19:6: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "Tfo$o2T", format),
            "23:6: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "foo_", format),
            "28:10: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_abc", format),
            "37:14: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "T$", format),
            "42:14: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "EE", format),
        };


        String filePath = builder.getFilePath("MethodTypeParameterNameInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
