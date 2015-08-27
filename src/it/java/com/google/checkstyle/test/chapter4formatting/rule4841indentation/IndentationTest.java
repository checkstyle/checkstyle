package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.google.checkstyle.test.base.IndentationConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class IndentationTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new IndentationConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void correctClassTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectClassInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctFieldTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectFieldAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctForTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectForAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctIfTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectIfAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctReturnTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectReturnAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctWhileTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectWhileDoWhileAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
