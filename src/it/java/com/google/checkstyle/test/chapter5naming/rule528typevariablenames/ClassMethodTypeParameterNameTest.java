package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck;

public class ClassMethodTypeParameterNameTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;
    private Class<ClassTypeParameterNameCheck> clazz = ClassTypeParameterNameCheck.class;
    private String msgKey = "name.invalidPattern";
    private static Configuration checkConfig;
    private static String format;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("ClassTypeParameterName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void testClassDefault() throws IOException, Exception {

        final String[] expected = {
            "5:31: " + getCheckMessage(checkConfig.getMessages(), msgKey, "t", format),
            "13:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "foo", format),
            "27:24: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$foo", format),
        };

        String filePath = builder.getFilePath("ClassTypeParameterNameInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testMethodDefault() throws IOException, Exception {

        Class<MethodTypeParameterNameCheck> clazz = MethodTypeParameterNameCheck.class;
        Configuration checkConfig = builder.getCheckConfig("MethodTypeParameterName");

        final String[] expected = {
            "9:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "e_e", format),
            "19:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Tfo$o2T", format),
            "23:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "foo_", format),
            "28:10: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_abc", format),
            "37:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "T$", format),
            "42:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "EE", format),
        };

        
        String filePath = builder.getFilePath("MethodTypeParameterNameInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}