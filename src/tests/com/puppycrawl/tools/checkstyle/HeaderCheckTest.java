package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.HeaderCheck;
import com.puppycrawl.tools.checkstyle.checks.RegexpHeaderCheck;

public class HeaderCheckTest extends BaseCheckTestCase
{
    public void testStaticHeader()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("inputHeader.java");
        final String[] expected = {
            "1: Missing a header - not enough lines in file."
        };
        verify(c, fname, expected);
    }

    public void testRegexpHeader()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        checkConfig.addAttribute("ignoreLines", "4,5");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
            "3: Line does not match expected header line of '// Created: 2002'."
        };
        verify(c, fname, expected);
    }

    public void testRegexpHeaderIgnore()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        checkConfig.addAttribute("ignoreLines", "3,4,5");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testNoHeader()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        // No header file specified
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");

        final String[] expected = {
            "1: Unable to check as missing lines to check."
        };

        verify(c, fname, expected);
    }

    public void testIllegalArgs()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("nonexisting.file"));
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }
}
