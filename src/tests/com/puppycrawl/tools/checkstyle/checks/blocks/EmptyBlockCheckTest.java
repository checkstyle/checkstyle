package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class EmptyBlockCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        final String[] expected = {
            "52:65: Must have at least one statement.",
            "54:41: Must have at least one statement.",
            "71:38: Must have at least one statement.",
            "72:52: Must have at least one statement.",
            "73:45: Must have at least one statement.",
            "75:13: Must have at least one statement.",
            "77:17: Must have at least one statement.",
            "79:13: Must have at least one statement.",
            "82:17: Must have at least one statement.",
            "178:5: Must have at least one statement.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testText()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.TEXT.toString());
        final String[] expected = {
            "52:65: Empty catch block.",
            "72:52: Empty catch block.",
            "73:45: Empty catch block.",
            "75:13: Empty try block.",
            "77:17: Empty finally block.",
            "178:5: Empty INSTANCE_INIT block.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testStatement()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.STMT.toString());
        final String[] expected = {
            "52:65: Must have at least one statement.",
            "54:41: Must have at least one statement.",
            "71:38: Must have at least one statement.",
            "72:52: Must have at least one statement.",
            "73:45: Must have at least one statement.",
            "75:13: Must have at least one statement.",
            "77:17: Must have at least one statement.",
            "79:13: Must have at least one statement.",
            "82:17: Must have at least one statement.",
            "178:5: Must have at least one statement.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
