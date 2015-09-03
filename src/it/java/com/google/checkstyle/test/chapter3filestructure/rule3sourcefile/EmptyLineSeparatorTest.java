package com.google.checkstyle.test.chapter3filestructure.rule3sourcefile;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck;

public class EmptyLineSeparatorTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void emptyLineSeparatorTest() throws Exception {
        
        Class<EmptyLineSeparatorCheck> clazz = EmptyLineSeparatorCheck.class;
        String messageKey = "empty.line.separator";

        final String[] expected = {
            "19: " + getCheckMessage(clazz, messageKey, "package"),
            "20: " + getCheckMessage(clazz, messageKey, "import"),
            "33: " + getCheckMessage(clazz, messageKey, "CLASS_DEF"),
            "37: " + getCheckMessage(clazz, messageKey, "STATIC_INIT"),
            "75: " + getCheckMessage(clazz, messageKey, "INTERFACE_DEF"),
            "82: " + getCheckMessage(clazz, messageKey, "INSTANCE_INIT"),
            "113: " + getCheckMessage(clazz, messageKey, "CLASS_DEF"),
            "119: " + getCheckMessage(clazz, messageKey, "VARIABLE_DEF"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyLineSeparator");
        String filePath = builder.getFilePath("EmptyLineSeparatorInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
