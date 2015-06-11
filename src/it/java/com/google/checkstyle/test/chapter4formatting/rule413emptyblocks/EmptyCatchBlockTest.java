package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck;

public class EmptyCatchBlockTest extends BaseCheckTestSupport
{
    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException
    {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void emptyBlockTestCatch() throws IOException, Exception
    {
        
        final String[] expected = {
            "28: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "49: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "71: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyCatchBlock");
        String filePath = builder.getFilePath("EmptyBlockInputCatch");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testNoViolations() throws IOException, Exception
    {
        
        final String[] expected = {
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyCatchBlock");
        String filePath = builder.getFilePath("EmptyCatchBlockNoViolationsInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByComment() throws IOException, Exception
    {
        
        final String[] expected = {
            "19: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "27: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyCatchBlock");
        String filePath = builder.getFilePath("EmptyCatchBlockViolationsByCommentInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByVariableName() throws IOException, Exception
    {
        final String[] expected = {
            "19: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "35: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "51: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "58: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        Configuration checkConfig = builder.getCheckConfig("EmptyCatchBlock");
        String filePath = builder.getFilePath("EmptyCatchBlockViolationsByVariableNameInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
