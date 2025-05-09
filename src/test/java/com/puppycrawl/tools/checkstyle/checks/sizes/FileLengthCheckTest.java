///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class FileLengthCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/filelength";
    }

    @Test
    void alarm() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, 228, 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileLength.java"), expected);
    }

    @Test
    void alarmDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLengthDefault.java"), expected);
    }

    @Test
    void fileLengthEqualToMaxLength() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLength2.java"), expected);
    }

    @Test
    void ok() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFileLength3.java"), expected);
    }

    @Test
    void args() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FileLengthCheck.class);
        try {
            checkConfig.addProperty("max", "abc");
            createChecker(checkConfig);
            assertWithMessage("Should indicate illegal args").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "sizes.FileLengthCheck - "
                    + "illegal value 'abc' for property 'max'");
        }
    }

    @Test
    void noAlarmByExtension() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputFileLength4.java"), expected);
    }

    @Test
    void extensions() {
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
        catch (IllegalArgumentException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Extensions array can not be null");
        }
    }

}
