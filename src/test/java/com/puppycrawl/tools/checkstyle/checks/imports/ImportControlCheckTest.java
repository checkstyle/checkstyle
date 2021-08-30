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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_DISALLOWED;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_MISSING_FILE;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_UNKNOWN_PKG;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ImportControlCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    @Test
    public void testGetRequiredTokens() {
        final ImportControlCheck checkObj = new ImportControlCheck();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testOne() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlOne.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlTwo.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl2.java"), expected);
    }

    @Test
    public void testWrong() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlWrong.xml"));
        final String[] expected = {"9:1: " + getCheckMessage(MSG_UNKNOWN_PKG)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl3.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl4.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", "   ");
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl5.java"), expected);
    }

    @Test
    public void testNull() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", null);
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl6.java"), expected);
    }

    @Test
    public void testUnknown() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", "unknown-file");
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl7.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertTrue(message.startsWith(message),
                    "Invalid message, should start with: " + messageStart);
        }
    }

    @Test
    public void testBroken() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlBroken.xml"));
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl8.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertTrue(message.startsWith(message),
                    "Invalid message, should start with: " + messageStart);
        }
    }

    @Test
    public void testOneRegExp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlOneRegExp.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl9.java"), expected);
    }

    @Test
    public void testTwoRegExp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlTwoRegExp.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl10.java"), expected);
    }

    @Test
    public void testNotRegExpNoMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlNotRegExpNoMatch.xml"));

        verifyWithInlineConfigParser(
                getPath("InputImportControl11.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlacklist() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlBlacklist.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Stream"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.Date"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Collectors"),
            "15:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.IntStream"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl_Blacklist.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchOne() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlStrategyOnMismatchOne.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl12.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchTwo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlStrategyOnMismatchTwo.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl13.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchThree() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlStrategyOnMismatchThree.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl14.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchFour() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlStrategyOnMismatchFour.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl15.java"), expected);
    }

    @Test
    public void testPkgRegExpInParent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlPkgRegExpInParent.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl16.java"), expected);
    }

    @Test
    public void testPkgRegExpInChild() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlPkgRegExpInChild.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl162.java"), expected);
    }

    @Test
    public void testPkgRegExpInBoth() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlPkgRegExpInBoth.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl163.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ImportControlCheck testCheckObject =
                new ImportControlCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testResource() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlOne.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl17.java"), expected);
    }

    @Test
    public void testResourceUnableToLoad() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("import-control_unknown.xml"));

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl18.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertTrue(message.startsWith(message),
                    "Invalid message, should start with: " + messageStart);
        }
    }

    @Test
    public void testUrlInFileProperty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getUriString("InputImportControlOne.xml"));
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl19.java"), expected);
    }

    @Test
    public void testUrlInFilePropertyUnableToLoad() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", "https://UnableToLoadThisURL");

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl20.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertTrue(message.startsWith(message),
                    "Invalid message, should start with: " + messageStart);
        }
    }

    @Test
    public void testCacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlOneRegExp.xml"));

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String filePath = File.createTempFile("empty", ".java", temporaryFolder).getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, filePath, expected);
        // One more time to use cache.
        verify(checkerConfig, filePath, expected);

        final String contents = new String(Files.readAllBytes(cacheFile.toPath()),
                StandardCharsets.UTF_8);
        assertTrue(contents.contains("InputImportControlOneRegExp.xml"),
                "External resource is not present in cache");
    }

    @Test
    public void testPathRegexMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addProperty("path", "^.*[\\\\/]src[\\\\/]test[\\\\/].*$");
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl21.java"), expected);
    }

    @Test
    public void testPathRegexMatchesPartially() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addProperty("path", "[\\\\/]InputImportControl22\\.java");
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl22.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addProperty("path", "^.*[\\\\/]src[\\\\/]main[\\\\/].*$");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl23.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatchPartially() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addProperty("path", "[\\\\/]NoMatch\\.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl24.java"), expected);
    }

    @Test
    public void testDisallowClassOfAllowPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file",
                getPath("InputImportControlDisallowClassOfAllowPackage.xml"));
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.Date"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlDisallowClassOfAllowPackage.java"),
                expected);
    }

    @Test
    public void testFileName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getResourcePath("InputImportControlFileName.xml"));
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlFileName.java"), expected);
    }

    @Test
    public void testFileNameNoExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file",
                getResourcePath("InputImportControlFileNameNoExtension.xml"));
        final DefaultConfiguration treewalkerConfig = createModuleConfig(TreeWalker.class);
        treewalkerConfig.addProperty("fileExtensions", "");
        treewalkerConfig.addChild(checkConfig);
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verify(treewalkerConfig, getPath("InputImportControlFileNameNoExtension"), expected);
    }

    /**
     * Returns String message of original exception that was thrown in
     * ImportControlCheck.setUrl or ImportControlCheck.setFile
     * and caught in test (it was caught and re-thrown twice after that)
     * Note: this is helper method with hard-coded structure of exception causes. It works
     * fine for methods mentioned, you may need to adjust it if you try to use it for other needs
     *
     * @param exception Exception
     * @return String message of original exception
     */
    private static String getCheckstyleExceptionMessage(CheckstyleException exception) {
        return exception.getCause().getCause().getCause().getCause().getMessage();
    }

}
