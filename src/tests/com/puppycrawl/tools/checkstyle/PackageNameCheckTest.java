package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.PackageNameCheck;

public class PackageNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "6:9: Name 'com.puppycrawl.tools.checkstyle' must match pattern '[A-Z]+'.",
        };
        verify(c, fname, expected);
    }

    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
