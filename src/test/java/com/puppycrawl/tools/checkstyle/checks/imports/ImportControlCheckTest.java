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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    @Test
    public void getRequiredTokens() {
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
    public void one() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl.java"), expected);
    }

    @Test
    public void two() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl2.java"), expected);
    }

    @Test
    public void wrong() throws Exception {
        final String[] expected = {"9:1: " + getCheckMessage(MSG_UNKNOWN_PKG)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl3.java"), expected);
    }

    @Test
    public void missing() throws Exception {
        final String[] expected = {"9:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verifyWithInlineConfigParser(
                getPath("InputImportControl4.java"), expected);
    }

    @Test
    public void empty() throws Exception {
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
    public void unknown() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl7.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException exc) {
            final String message = getCheckstyleExceptionMessage(exc);
            final String messageStart = "Unable to find: ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void broken() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(getPath("InputImportControl8.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException exc) {
            final String message = getCheckstyleExceptionMessage(exc);
            final String messageStart = "Unable to load ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void oneRegExp() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl9.java"), expected);
    }

    @Test
    public void twoRegExp() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl10.java"), expected);
    }

    @Test
    public void notRegExpNoMatch() throws Exception {

        verifyWithInlineConfigParser(
                getPath("InputImportControl11.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void blacklist() throws Exception {
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
    public void strategyOnMismatchOne() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl12.java"), expected);
    }

    @Test
    public void strategyOnMismatchTwo() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "14:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl13.java"), expected);
    }

    @Test
    public void strategyOnMismatchThree() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl14.java"), expected);
    }

    @Test
    public void strategyOnMismatchFour() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControl15.java"), expected);
    }

    @Test
    public void withoutRegexAndWithStrategyOnMismatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputImportControlWithoutRegexAndWithStrategyOnMismatch.java"),
            expected);
    }

    @Test
    public void pkgRegExpInParent() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl16.java"), expected);
    }

    @Test
    public void pkgRegExpInChild() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl162.java"), expected);
    }

    @Test
    public void pkgRegExpInBoth() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl163.java"), expected);
    }

    @Test
    public void getAcceptableTokens() {
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
    public void resource() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl17.java"), expected);
    }

    @Test
    public void resourceUnableToLoad() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(getPath("InputImportControl18.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException exc) {
            final String message = getCheckstyleExceptionMessage(exc);
            final String messageStart = "Unable to find: ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void urlInFileProperty() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl19.java"), expected);
    }

    @Test
    public void urlInFilePropertyUnableToLoad() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(
                    getPath("InputImportControl20.java"), expected);
            assertWithMessage("Test should fail if exception was not thrown").fail();
        }
        catch (CheckstyleException exc) {
            final String message = getCheckstyleExceptionMessage(exc);
            final String messageStart = "Unable to load ";

            assertWithMessage("Invalid message, should start with: %s", messageStart)
                    .that(message)
                    .startsWith(messageStart);
        }
    }

    @Test
    public void cacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
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
    public void pathRegexMatches() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl21.java"), expected);
    }

    @Test
    public void pathRegexMatchesPartially() throws Exception {
        final String[] expected = {"13:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verifyWithInlineConfigParser(
                getPath("InputImportControl22.java"), expected);
    }

    @Test
    public void pathRegexDoesntMatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl23.java"), expected);
    }

    @Test
    public void pathRegexDoesntMatchPartially() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControl24.java"), expected);
    }

    @Test
    public void disallowClassOfAllowPackage() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.Date"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlDisallowClassOfAllowPackage.java"),
                expected);
    }

    @Test
    public void fileName() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlFileName.java"), expected);
    }

    @Test
    public void withRegex() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File"),
        };

        verifyWithInlineConfigParser(
            getPath("InputImportControlWithRegex.java"), expected);
    }

    @Test
    public void fileNameNoExtension() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlFileNameNoExtension"), expected);
    }

    @Test
    public void beginTreeCurrentImportControl() throws Exception {
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
    public void importControlFileName() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verifyWithInlineConfigParser(
                getPath("InputImportControlTestRegexpInFile.java"), expected);
    }

    @Test
    public void importControlFileName2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputImportControlTestRegexpInFile2.java"), expected);
    }

    @Test
    public void importControlTestException() {
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
