////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FileLengthCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/filelength";
    }

    @Test
    public void testAlarm() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", "20");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, 225, 20),
        };
        verify(checkConfig,
                getPath("InputFileLength.java"), expected);
    }

    @Test
    public void testFileLengthEqualToMaxLength() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", "225");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputFileLength.java"), expected);
    }

    @Test
    public void testOk() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", "1000");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputFileLength.java"), expected);
    }

    @Test
    public void testArgs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FileLengthCheck.class);
        try {
            checkConfig.addAttribute("max", "abc");
            createChecker(checkConfig);
            fail("Should indicate illegal args");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "sizes.FileLengthCheck - "
                    + "illegal value 'abc' for property 'max'",
                ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testNoAlarmByExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FileLengthCheck.class);
        checkConfig.addAttribute("fileExtensions", "txt");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputFileLength.java"), expected);
    }

    @Test
    public void testExtensions() {
        final FileLengthCheck check = new FileLengthCheck();
        check.setFileExtensions("java");
        assertEquals(".java", check.getFileExtensions()[0], "extension should be the same");
        check.setFileExtensions(".java");
        assertEquals(".java", check.getFileExtensions()[0], "extension should be the same");
        try {
            check.setFileExtensions((String[]) null);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Extensions array can not be null", ex.getMessage(),
                    "Invalid exception message");
        }
    }

}
