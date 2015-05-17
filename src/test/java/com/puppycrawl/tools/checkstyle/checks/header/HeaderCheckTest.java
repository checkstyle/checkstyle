////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.header;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISSING;

public class HeaderCheckTest extends BaseFileSetCheckTestSupport {
    @Test
    public void testStaticHeader() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("configs/java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };
        verify(checkConfig, getPath("inputHeader.java"), expected);
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testRegexpHeaderURL() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        URI uri = new File(getPath("regexp.header")).toURI();
        checkConfig.addAttribute("headerFile", uri.toString());
        final String[] expected = {
            "3: " + getCheckMessage(MSG_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testInlineRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testFailureForMultilineRegexp() throws Exception {
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
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header1"));
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti1() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader2.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti3() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 7");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti4() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 5, 6, 7");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRegexpHeader3.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti5() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeader4.java"), expected);
    }

    @Test
    public void testRegexpHeaderSmallHeader() throws Exception {
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
        throws Exception {
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
    public void testNonExistingHeaderFile() throws Exception {
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
    public void testInvalidCharset() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("config/java.header"));
        checkConfig.addAttribute("charset", "XSO-8859-1");
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }

    @Test
    public void testEmptyFilename() throws Exception {
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
