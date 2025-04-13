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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_DISALLOWED;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_MISSING_FILE;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck.MSG_UNKNOWN_PKG;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testOne() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception {
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
        final String[] expected = {"9:1: " + getCheckMessage(MSG_UNKNOWN_PKG)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl3.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl4.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception {
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl5.java"), expected);
    }

    @Test
    public void testNull() throws Exception {
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl6.java"), expected);
    }

    @Test
    public void testUnknown() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl7.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void testBroken() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(getPath("InputImportControl8.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void testOneRegExp() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl9.java"), expected);
    }

    @Test
    public void testTwoRegExp() throws Exception {
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

        verifyWithInlineConfigParser(
                getPath("InputImportControl11.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlacklist() throws Exception {
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
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl13.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchThree() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl14.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchFour() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl15.java"), expected);
    }

    @Test
    public void testWithoutRegexAndWithStrategyOnMismatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputImportControlWithoutRegexAndWithStrategyOnMismatch.java"),
            expected);
    }

    @Test
    public void testPkgRegExpInParent() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl16.java"), expected);
    }

    @Test
    public void testPkgRegExpInChild() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl162.java"), expected);
    }

    @Test
    public void testPkgRegExpInBoth() throws Exception {
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

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testResource() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl17.java"), expected);
    }

    @Test
    public void testResourceUnableToLoad() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(getPath("InputImportControl18.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void testUrlInFileProperty() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl19.java"), expected);
    }

    @Test
    public void testUrlInFilePropertyUnableToLoad() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl20.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void testCacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addProperty("file", getPath("InputImportControlOneRegExp.xml"));

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        final String uniqueFileName1 = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName1);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String uniqueFileName2 = "empty_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName2);

        execute(checkerConfig, filePath.toString());
        // One more time to use cache.
        execute(checkerConfig, filePath.toString());

        final String contents = Files.readString(cacheFile.toPath());
        assertWithMessage("External resource is not present in cache")
                .that(contents)
                .contains("InputImportControlOneRegExp.xml");
    }

    @Test
    public void testPathRegexMatches() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl21.java"), expected);
    }

    @Test
    public void testPathRegexMatchesPartially() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl22.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl23.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatchPartially() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl24.java"), expected);
    }

    @Test
    public void testDisallowClassOfAllowPackage() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.Date"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlDisallowClassOfAllowPackage.java"),
                expected);
    }

    @Test
    public void testFileName() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlFileName.java"), expected);
    }

    @Test
    public void testWithRegex() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File"),
        };

        verifyWithInlineConfigParser(
            getPath("InputImportControlWithRegex.java"), expected);
    }

    @Test
    public void testFileNameNoExtension() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlFileNameNoExtension"), expected);
    }

    @Test
    public void testBeginTreeCurrentImportControl() throws Exception {
        final String file1 = getPath("InputImportControlBeginTree1.java");
        final String file2 = getPath("InputImportControlBeginTree2.java");
        final List<String> expectedFirstInput = Arrays.asList(
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Stream"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Collectors")
        );
        final List<String> expectedSecondInput = Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);
        verifyWithInlineConfigParser(file1, file2, expectedFirstInput, expectedSecondInput);
    }

    @Test
    public void testImportControlFileName() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlTestRegexpInFile.java"), expected);
    }

    @Test
    public void testImportControlFileName2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControlTestRegexpInFile2.java"), expected);
    }

    @Test
    public void testImportControlTestException() {
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> {
                    verifyWithInlineConfigParser(getPath("InputImportControlTestException.java"));
                });

        assertThat(ex.getCause().getCause().getCause().getCause().getCause().getMessage())
                .startsWith("unable to parse file:");
        assertThat(ex.getCause().getCause().getCause().getCause().getCause().getMessage())
                .endsWith("- Document root element \"import-control\", must match DOCTYPE"
                 + " root \"null\".");
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
