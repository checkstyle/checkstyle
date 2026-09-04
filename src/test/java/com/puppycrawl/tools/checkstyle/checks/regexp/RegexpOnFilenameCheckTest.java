///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MATCH;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.io.Serial;
import java.util.Collections;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpOnFilenameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexponfilename";
    }

    @Test
    public void testDefaultConfigurationOnValidInput() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameDefaultConfigurationOnValidInput.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testDefaultProperties() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "\\s"),
        };
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilename Space.properties"), expected);
    }

    @Test
    public void testMatchFileMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", ".*\\.java"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFileMatches.java"), expected);
    }

    @Test
    public void testMatchFileNotMatches() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameMatchFileNotMatches.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFileMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "", ".*\\.properties"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameNotMatchFileMatches.java"), expected);
    }

    @Test
    public void testNotMatchFileNotMatches() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameNotMatchFileNotMatches.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFolderMatches.java"), expected);
    }

    @Test
    public void testMatchFolderNotMatches() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameMatchFolderNotMatches.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]gov[\\\\/].*", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameNotMatchFolderMatches.java"), expected);
    }

    @Test
    public void testNotMatchFolderNotMatches() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameNotMatchFolderNotMatches.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ".*\\.java"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFolderAndFileMatches.java"), expected);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesBoth() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFolderAndFileNotMatchesBoth.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesFile() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFolderAndFileNotMatchesFile.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesFolder() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameMatchFolderAndFileNotMatchesFolder.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderAndFileMatches() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]com[\\\\/].*", ".*\\.dat"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameNotMatchFolderAndFileMatches.java"), expected);
    }

    @Test
    public void testNotMatchFolderAndFileNotMatchesFolder() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameNotMatchFolderAndFileNotMatchesFolder.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderAndFileNotMatchesFile() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpOnFilenameNotMatchFolderAndFileNotMatchesFile.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreExtension() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameIgnoreExtension.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreExtensionNoExtension() throws Exception {
        verifyWithInlineConfigParser(getPath("InputRegexpOnFilenameNoExtension"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testException() throws Exception {
        // escape character needed for testing IOException from File.getCanonicalPath on all OSes
        final File file = new File(getPath("") + "\u0000" + File.separatorChar + "Test");
        final RegexpOnFilenameCheck check = new RegexpOnFilenameCheck();
        check.setFileNamePattern(Pattern.compile("BAD"));
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> check.process(file, new FileText(file, Collections.emptyList())),
                "CheckstyleException expected");
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("unable to create canonical path names for " + file);
    }

    @Test
    public void testWithFileWithoutParent() throws Exception {
        final DefaultConfiguration moduleConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        final String path = getPath("package-info.java");
        final File fileWithoutParent = new MockFile(path);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(moduleConfig), new File[] {fileWithoutParent}, path, expected);
    }

    private static final class MockFile extends File {

        /** A unique serial version identifier. */
        @Serial
        private static final long serialVersionUID = 8361197804062781531L;

        private MockFile(String path) {
            super(path);
        }

        /** This method is overridden to emulate a file without parent. */
        @Override
        public String getParent() {
            return null;
        }

    }

}
