package com.google.checkstyle.test.chapter2filebasic.rule21filename;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck;

public class OuterTypeFilenameTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void outerTypeFilenameTest_1() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_1");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void outerTypeFilenameTest_2() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_2");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void outerTypeFilenameTest_3() throws Exception {

        final String[] expected = {
            "3: " + getCheckMessage(OuterTypeFilenameCheck.class, "type.file.mismatch"),
        };

        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_3");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
