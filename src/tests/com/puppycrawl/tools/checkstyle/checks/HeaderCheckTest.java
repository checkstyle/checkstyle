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

    public void testRegexpHeader() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        final String[] expected = {
            "3: Line does not match expected header line of '// Created: 2002'."
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testInlineRegexpHeader()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: Line does not match expected header line of '// Created: 2002'."
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testFailureForMultilineRegexp()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^(.*\\n.*)");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed when regexp spans multiple lines");
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }

    public void testRegexpHeaderIgnore() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header1"));
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testRegexpHeaderMulti1() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    public void testRegexpHeaderMulti2() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader2.java"), expected);
    }

    public void testRegexpHeaderMulti3() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 7");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    public void testRegexpHeaderMulti4() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 5, 6, 7");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader3.java"), expected);
    }

    public void testRegexpHeaderMulti5() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3");
        final String[] expected = {
            "1: Missing a header - not enough lines in file."
        };
        verify(checkConfig, getPath("InputRegexpHeader4.java"), expected);
    }

    public void testRegexpHeaderSmallHeader() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpSmallHeader.java"), expected);
    }

    public void testNoHeader()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        // No header file specified
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }

    public void testNonExistingHeaderFile()
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

    public void testEmptyFilename()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", "");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with invalid headerFile");
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }
}
