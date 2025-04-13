///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck.MSG_KEY;

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
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, 228, 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileLength.java"), expected);
    }

    @Test
    public void testAlarmDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLengthDefault.java"), expected);
    }

    @Test
    public void testFileLengthEqualToMaxLength() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLength2.java"), expected);
    }

    @Test
    public void testOk() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLength3.java"), expected);
    }

    @Test
    public void testArgs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FileLengthCheck.class);
        try {
            checkConfig.addProperty("max", "abc");
            createChecker(checkConfig);
            assertWithMessage("Should indicate illegal args").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "sizes.FileLengthCheck - "
                    + "illegal value 'abc' for property 'max'");
        }
    }

    @Test
    public void testNoAlarmByExtension() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputFileLength4.java"), expected);
    }

    @Test
    public void testExtensions() {
        final FileLengthCheck check = new FileLengthCheck();
        check.setFileExtensions("java");
        assertWithMessage("extension should be the same")
            .that(check.getFileExtensions()[0])
            .isEqualTo(".java");
        check.setFileExtensions(".java");
        assertWithMessage("extension should be the same")
            .that(check.getFileExtensions()[0])
            .isEqualTo(".java");
        try {
            check.setFileExtensions((String[]) null);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Extensions array can not be null");
        }
    }

}
