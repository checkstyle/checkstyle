package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.TypeLeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.LeftCurlyOption;

public class TypeLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(TypeLeftCurlyCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected = {
            "8:1: '{' should be on the previous line.",
            "12:5: '{' should be on the previous line.",
            "21:5: '{' should be on the previous line.",
            "30:5: '{' should be on the previous line.",
            "39:5: '{' should be on the previous line.",
        };
        verify(c, fname, expected);
    }

    public void testNL()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(TypeLeftCurlyCheck.class.getName());
        checkConfig.addProperty("option", LeftCurlyOption.NL.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testNLOW()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(TypeLeftCurlyCheck.class.getName());
        checkConfig.addProperty("option", LeftCurlyOption.NLOW.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected = {
            "8:1: '{' should be on the previous line.",
            "12:5: '{' should be on the previous line.",
            "21:5: '{' should be on the previous line.",
            "30:5: '{' should be on the previous line.",
            "39:5: '{' should be on the previous line.",
        };
        verify(c, fname, expected);
    }
}
