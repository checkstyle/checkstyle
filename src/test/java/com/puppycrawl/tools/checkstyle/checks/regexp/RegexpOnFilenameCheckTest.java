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
    public void defaultConfigurationOnValidInput() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void defaultProperties() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        final String path = getPath("InputRegexpOnFilename Space.properties");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "\\s"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void matchFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", ".*\\.java"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void matchFileNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("fileNamePattern", "BAD.*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void notMatchFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("fileNamePattern", ".*\\.properties");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "", ".*\\.properties"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void notMatchFileNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void matchFolderMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ""),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void matchFolderNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", "BAD.*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void notMatchFolderMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]gov[\\\\/].*");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]gov[\\\\/].*", ""),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void notMatchFolderNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void matchFolderAndFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ".*\\.java"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void matchFolderAndFileNotMatchesBoth() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", "BAD.*");
        checkConfig.addProperty("fileNamePattern", ".*\\.properties");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void matchFolderAndFileNotMatchesFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        checkConfig.addProperty("fileNamePattern", ".*\\.properties");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void matchFolderAndFileNotMatchesFolder() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "true");
        checkConfig.addProperty("folderPattern", "BAD.*");
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void notMatchFolderAndFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]com[\\\\/].*");
        checkConfig.addProperty("fileNamePattern", ".*\\.dat");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]com[\\\\/].*", ".*\\.dat"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void notMatchFolderAndFileNotMatchesFolder() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]javastrangefolder[\\\\/].*");
        checkConfig.addProperty("fileNamePattern", ".*\\.dat");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void notMatchFolderAndFileNotMatchesFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("match", "false");
        checkConfig.addProperty("folderPattern", ".*[\\\\/]govstrangefolder[\\\\/].*");
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void ignoreExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("fileNamePattern", ".*\\.java");
        checkConfig.addProperty("ignoreFileNameExtensions", "true");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void ignoreExtensionNoExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addProperty("fileNamePattern", "\\.");
        checkConfig.addProperty("ignoreFileNameExtensions", "true");
        verify(checkConfig, getPath("InputRegexpOnFilenameNoExtension"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void exception() throws Exception {
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
    public void withFileWithoutParent() throws Exception {
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
