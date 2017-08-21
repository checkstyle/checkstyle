////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class ImportControlCheckTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    private static String getResourcePath(String filename) {
        return "/com/puppycrawl/tools/checkstyle/checks/imports/importcontrol/" + filename;
    }

    @Test
    public void testGetRequiredTokens() {
        final ImportControlCheck checkObj = new ImportControlCheck();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
        };
        assertArrayEquals("Default required tokens are invalid",
            expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testOne() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlOne.xml"));
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlTwo.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "4:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "6:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testWrong() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlWrong.xml"));
        final String[] expected = {"1:1: " + getCheckMessage(MSG_UNKNOWN_PKG)};
        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        final String[] expected = {"1:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "   ");
        final String[] expected = {"1:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testNull() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", null);
        final String[] expected = {"1:1: " + getCheckMessage(MSG_MISSING_FILE)};
        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testUnknown() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "unknown-file");
        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertTrue("Invalid message, should start with: " + messageStart,
                message.startsWith(message));
        }
    }

    @Test
    public void testBroken() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlBroken.xml"));
        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertTrue("Invalid message, should start with: " + messageStart,
                message.startsWith(message));
        }
    }

    @Test
    public void testOneRegExp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlOneRegExp.xml"));
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testTwoRegExp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlTwoRegExp.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "4:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "6:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testBlacklist() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlBlacklist.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Stream"),
            "4:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.Date"),
            "6:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.Collectors"),
            "7:1: " + getCheckMessage(MSG_DISALLOWED, "java.util.stream.IntStream"),
        };

        verify(checkConfig, getPath("InputImportControl_Blacklist.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchOne() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlStrategyOnMismatchOne.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "4:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
            "6:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchTwo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlStrategyOnMismatchTwo.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "6:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Button.ABORT"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchThree() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlStrategyOnMismatchThree.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testStrategyOnMismatchFour() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlStrategyOnMismatchFour.xml"));
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_DISALLOWED, "java.awt.Image"),
            "4:1: " + getCheckMessage(MSG_DISALLOWED, "javax.swing.border.*"),
        };

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testPkgRegExpInParent() throws Exception {
        testRegExpInPackage("InputImportControlPkgRegExpInParent.xml");
    }

    @Test
    public void testPkgRegExpInChild() throws Exception {
        testRegExpInPackage("InputImportControlPkgRegExpInChild.xml");
    }

    @Test
    public void testPkgRegExpInBoth() throws Exception {
        testRegExpInPackage("InputImportControlPkgRegExpInBoth.xml");
    }

    // all import-control_pkg-re* files should be equivalent so use one test for all
    private void testRegExpInPackage(String file) throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath(file));
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
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

        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testResource() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("InputImportControlOne.xml"));
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testResourceUnableToLoad() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("import-control_unknown.xml"));

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to find: ";

            assertTrue("Invalid message, should start with: " + messageStart,
                message.startsWith(message));
        }
    }

    @Test
    public void testUrlInFileProperty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getUriString("InputImportControlOne.xml"));
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testUrlInFilePropertyUnableToLoad() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "https://UnableToLoadThisURL");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputImportControl.java"), expected);
            fail("Test should fail if exception was not thrown");
        }
        catch (CheckstyleException ex) {
            final String message = getCheckstyleExceptionMessage(ex);
            final String messageStart = "Unable to load ";

            assertTrue("Invalid message, should start with: " + messageStart,
                message.startsWith(message));
        }
    }

    @Test
    public void testCacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getPath("InputImportControlOneRegExp.xml"));
        final File cacheFile = temporaryFolder.newFile();
        final Checker checker = createMockCheckerWithCache(checkConfig, cacheFile);

        final String filePath = temporaryFolder.newFile("EmptyFile.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, filePath, filePath, expected);
        // One more time to use cache.
        verify(checker, filePath, filePath, expected);

        assertTrue("External resourse is not present in cache",
                new String(Files.readAllBytes(cacheFile.toPath()),
                Charset.forName("UTF-8")).contains("InputImportControlOneRegExp.xml"));
    }

    @Test
    public void testPathRegexMatches() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addAttribute("path", "^.*[\\\\/]src[\\\\/]test[\\\\/].*$");
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testPathRegexMatchesPartially() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addAttribute("path", "[\\\\/]InputImportControl\\.java");
        final String[] expected = {"5:1: " + getCheckMessage(MSG_DISALLOWED, "java.io.File")};

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addAttribute("path", "^.*[\\\\/]src[\\\\/]main[\\\\/].*$");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    @Test
    public void testPathRegexDoesntMatchPartially() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", getResourcePath("InputImportControlOne.xml"));
        checkConfig.addAttribute("path", "[\\\\/]NoMatch\\.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputImportControl.java"), expected);
    }

    private Checker createMockCheckerWithCache(DefaultConfiguration checkConfig,
                                               File cacheFile) throws CheckstyleException {
        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(treeWalkerConfig);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());
        return checker;
    }

    /**
     * Returns String message of original exception that was thrown in
     * ImportControlCheck.setUrl or ImportControlCheck.setFile
     * and caught in test (it was caught and re-thrown twice after that)
     * Note: this is helper method with hard-coded structure of exception causes. It works
     * fine for methods mentioned, you may need to adjust it if you try to use it for other needs
     * @param exception Exception
     * @return String message of original exception
     */
    private static String getCheckstyleExceptionMessage(CheckstyleException exception) {
        return exception.getCause().getCause().getCause().getMessage();
    }
}
