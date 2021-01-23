////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MATCH;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MISMATCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
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
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexponfilename";
    }

    @Test
    public void testDefaultConfigurationOnValidInput() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testDefaultProperties() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        final String path = getPath("InputRegexpOnFilename Space.properties");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "\\s"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testMatchFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", ".*\\.java"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testMatchFileNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("fileNamePattern", "BAD.*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("fileNamePattern", ".*\\.properties");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "", ".*\\.properties"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testNotMatchFileNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ""),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testMatchFolderNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", "BAD.*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]gov[\\\\/].*");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]gov[\\\\/].*", ""),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testNotMatchFolderNotMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, ".*[\\\\/]resources[\\\\/].*", ".*\\.java"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesBoth() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", "BAD.*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.properties");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]resources[\\\\/].*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.properties");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatchFolderAndFileNotMatchesFolder() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "true");
        checkConfig.addAttribute("folderPattern", "BAD.*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderAndFileMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]com[\\\\/].*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.dat");
        final String path = getPath("InputRegexpOnFilenameSemantic.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, ".*[\\\\/]com[\\\\/].*", ".*\\.dat"),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testNotMatchFolderAndFileNotMatchesFolder() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]javastrangefolder[\\\\/].*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.dat");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNotMatchFolderAndFileNotMatchesFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("match", "false");
        checkConfig.addAttribute("folderPattern", ".*[\\\\/]govstrangefolder[\\\\/].*");
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("fileNamePattern", ".*\\.java");
        checkConfig.addAttribute("ignoreFileNameExtensions", "true");
        verify(checkConfig, getPath("InputRegexpOnFilenameSemantic.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreExtensionNoExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpOnFilenameCheck.class);
        checkConfig.addAttribute("fileNamePattern", "\\.");
        checkConfig.addAttribute("ignoreFileNameExtensions", "true");
        verify(checkConfig, getPath("InputRegexpOnFilenameNoExtension"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testException() throws Exception {
        // escape character needed for testing IOException from File.getCanonicalPath on all OSes
        final File file = new File(getPath("") + "\u0000" + File.separatorChar + "Test");
        try {
            final RegexpOnFilenameCheck check = new RegexpOnFilenameCheck();
            check.setFileNamePattern(Pattern.compile("BAD"));
            check.process(file, new FileText(file, Collections.emptyList()));
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("unable to create canonical path names for " + file.getAbsolutePath(),
                ex.getMessage(), "Invalid exception message");
        }
    }

}
