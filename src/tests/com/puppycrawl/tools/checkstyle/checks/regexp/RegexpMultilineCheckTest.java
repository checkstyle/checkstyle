package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.checks.RegexpCheck;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RegexpMultilineCheckTest extends BaseFileSetCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RegexpMultilineCheck.class);
    }

    @Test
    public void testIt()
            throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
        throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "false");
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit()
            throws Exception
    {
        final String illegal = "^import";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "7: Line matches the illegal pattern '" + illegal + "'.",
            "8: Line matches the illegal pattern '" + illegal + "'.",
            "9: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }
}
