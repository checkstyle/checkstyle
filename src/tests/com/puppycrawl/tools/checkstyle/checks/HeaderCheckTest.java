package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class HeaderCheckTest extends BaseCheckTestCase
{
    public void testStaticHeader()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "1: Missing a header - not enough lines in file."
        };
        verify(checkConfig, getPath("inputHeader.java"), expected);
    }

    public void testRegexpHeader()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        checkConfig.addAttribute("ignoreLines", "4,5");
        final String[] expected = {
            "3: Line does not match expected header line of '// Created: 2002'."
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testRegexpHeaderIgnore()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        checkConfig.addAttribute("ignoreLines", "3,4,5");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testNoHeader()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        // No header file specified
        final String[] expected = {
            "1: Unable to check as missing lines to check."
        };

        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
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
