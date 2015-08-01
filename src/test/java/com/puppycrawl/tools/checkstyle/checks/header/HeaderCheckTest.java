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

import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISSING;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

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
    public void testNoHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(HeaderCheck.class);
        try {
            createChecker(checkConfig);
            final String[] expected = {
            };
            verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
        }
        catch (CheckstyleException ex) {
            // Exception is not expected
            fail();
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

    @Test
    public void testNotMatch() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("configs/java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_MISMATCH,
                    "// checkstyle: Checks Java source code for adherence to a set of rules."),
        };
        verify(checkConfig, getPath("configs/java2.header"), expected);
    }

    @Test
    public void testIgnore() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("configs/java.header"));
        checkConfig.addAttribute("ignoreLines", "2");
        final String[] expected = {
        };
        verify(checkConfig, getPath("configs/java2.header"), expected);
    }

}
