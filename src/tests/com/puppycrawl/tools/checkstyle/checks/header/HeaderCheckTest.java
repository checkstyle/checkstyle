package com.puppycrawl.tools.checkstyle.checks.header;

import static org.junit.Assert.fail;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Test;

public class HeaderCheckTest extends BaseFileSetCheckTestSupport
{
    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void testRegexpHeaderIgnore() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header1"));
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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
