package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.OtherLeftCurlyCheck;

public class OtherLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public OtherLeftCurlyCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(OtherLeftCurlyCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
            "19:9: '{' should be on the previous line.",
            "21:13: '{' should be on the previous line.",
            "23:17: '{' should be on the previous line.",
            "30:17: '{' should be on the previous line.",
            "34:17: '{' should be on the previous line.",
            "42:13: '{' should be on the previous line.",
            "46:13: '{' should be on the previous line.",
            "52:9: '{' should be on the previous line.",
            "54:13: '{' should be on the previous line.",
        };
        verify(c, fname, expected);
    }

    public void testNL()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(OtherLeftCurlyCheck.class.getName());
        checkConfig.addProperty("option", LeftCurlyOption.NL.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
            "26:33: '{' should be on a new line."
        };
        verify(c, fname, expected);
    }

    public void testIgnore()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(OtherLeftCurlyCheck.class.getName());
        checkConfig.addProperty("option", LeftCurlyOption.IGNORE.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
