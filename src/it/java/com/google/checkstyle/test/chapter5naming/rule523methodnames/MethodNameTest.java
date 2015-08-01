package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class MethodNameTest extends BaseCheckTestSupport{
    
	private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void methodNameTest() throws IOException, Exception {
        
        Configuration checkConfig = builder.getCheckConfig("MethodName");
        String msgKey = "name.invalidPattern";
        String format = "^[a-z][a-z0-9][a-zA-Z0-9_]*$";

        final String[] expected = {
            "11:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo", format),
            "12:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fOo", format),
            "14:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f$o", format),
            "15:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f_oo", format),
            "16:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f", format),
            "17:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fO", format),
            "21:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo", format),
            "22:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fOo", format),
            "24:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f$o", format),
            "25:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f_oo", format),
            "26:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f", format),
            "27:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fO", format),
            "32:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo", format),
            "33:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fOo", format),
            "35:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f$o", format),
            "36:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f_oo", format),
            "37:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f", format),
            "38:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fO", format),
            "44:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo", format),
            "45:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fOo", format),
            "47:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f$o", format),
            "48:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f_oo", format),
            "49:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "f", format),
            "50:14: " + getCheckMessage(checkConfig.getMessages(), msgKey, "fO", format),
        };
        
        String filePath = builder.getFilePath("InputMethodName");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
