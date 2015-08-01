package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.io.File;
import java.io.IOException;

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
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new IndentationConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void correctClassTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectClassInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctFieldTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectFieldAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctForTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectForAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctIfTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectIfAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctReturnTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectReturnAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void correctWhileTest() throws IOException, Exception {

        final String[] expected = {};

        Configuration checkConfig = builder.getCheckConfig("Indentation");
        String filePath = builder.getFilePath("IndentationCorrectWhileDoWhileAndParameterInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
