package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.EmptyBlockCheck;
import com.puppycrawl.tools.checkstyle.checks.BlockOption;

public class EmptyBlockCheckTest
    extends BaseCheckTestCase
{
    public EmptyBlockCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(EmptyBlockCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
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
        };
        verify(c, fname, expected);
    }

    public void testText()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(EmptyBlockCheck.class.getName());
        checkConfig.addProperty("option", BlockOption.TEXT.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "52:65: Empty catch block.",
            "72:52: Empty catch block.",
            "73:45: Empty catch block.",
            "75:13: Empty try block.",
            "77:17: Empty finally block.",
        };
        verify(c, fname, expected);
    }

    public void testStatement()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(EmptyBlockCheck.class.getName());
        checkConfig.addProperty("option", BlockOption.STMT.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
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
        };
        verify(c, fname, expected);
    }
}
