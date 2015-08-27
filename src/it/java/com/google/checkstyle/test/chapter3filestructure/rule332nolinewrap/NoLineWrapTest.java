package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck;

public class NoLineWrapTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void badLineWrapTest() throws Exception {

        final String[] expected = {
            "1: " + getCheckMessage(NoLineWrapCheck.class, "no.line.wrap", "package"),
            "6: " + getCheckMessage(NoLineWrapCheck.class, "no.line.wrap", "import"),
        };

        Configuration checkConfig = builder.getCheckConfig("NoLineWrap");
        String filePath = builder.getFilePath("NoLineWrap_Bad");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void goodLineWrapTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        Configuration checkConfig = builder.getCheckConfig("NoLineWrap");
        String filePath = builder.getFilePath("NoLineWrap_Good");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void goodLineLength() throws Exception {

        int maxLineLength = 100;
        final String[] expected = {
                "5: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 112),
                "29: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 183),
                "46: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 131),
                "47: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 124),
                "48: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 113),
                "50: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 116),
                "53: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 131),
                "57: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 116),
        };

        Configuration checkConfig = builder.getCheckConfig("LineLength");
        String filePath = builder.getFilePath("LineLengthInput2");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
