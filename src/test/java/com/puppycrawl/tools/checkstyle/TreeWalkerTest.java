////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;

public class TreeWalkerTest extends BaseCheckTestSupport {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testProperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.java");
        try (final Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            final String content = "public class Main { public static final int k = 5 + 4; }";
            writer.write(content);
        }
        final String[] expected1 = {
            "1:45: Name 'k' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig, file.getPath(), expected1);
    }

    @Test
    public void testImproperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        try (final BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            final String content = "public class Main { public static final int k = 5 + 4; }";
            writer.write(content);
        }
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testAcceptableTokens()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF,"
                + "IMPORT");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        try {
            verify(checkConfig, getPath("InputMain.java"), expected);
            fail();
        }
        catch (CheckstyleException ex) {
            final String errorMsg = ex.getMessage();
            assertTrue(errorMsg.contains("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.TreeWalker - Token \"IMPORT\""
                    + " was not found in Acceptable tokens list in check"
                    + " com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck"));
        }
    }

    @Test
    public void testOnEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, pathToEmptyFile, expected);
    }

    @Test
    public void testWithCheckNotHavingTreeWalkerAsParent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        try {
            verify(checkConfig, temporaryFolder.newFile().getPath(), expected);
            fail();
        }
        catch (CheckstyleException exception) {
            assertTrue(exception.getMessage().contains("TreeWalker is not allowed as a parent of"));
        }
    }

    @Test
    public void testSettersForParameters() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setTabWidth(1);
        treeWalker.configure(new DefaultConfiguration("default config"));
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
    }

    @Test
    public void testDestroyNonExistingCache() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(new DefaultConfiguration("default config"));
        if (System.getProperty("os.name")
                        .toLowerCase(Locale.ENGLISH).startsWith("windows")) {
            // https://support.microsoft.com/en-us/kb/177506 but this only for NTFS
            // WindowsServer 2012 use Resilient File System (ReFS), so any name is ok
            final File file = new File("C\\:invalid");
            treeWalker.setCacheFile(file.getAbsolutePath());
        }
        else {
            treeWalker.setCacheFile(File.separator + ":invalid");
        }
        try {
            treeWalker.destroy();
            fail("Exception did not happen");
        }
        catch (IllegalStateException ex) {
            assertTrue(ex.getCause() instanceof IOException);
        }
    }

    @Test
    public void testCacheFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        // one more time to reuse cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testCacheFileChangeInConfig() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);

        // update Checker config
        //checker.destroy();
        //checker.configure(checkerConfig);

        final Checker otherChecker = new Checker();
        otherChecker.setLocaleCountry(locale.getCountry());
        otherChecker.setLocaleLanguage(locale.getLanguage());
        otherChecker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        otherChecker.configure(checkerConfig);
        otherChecker.addListener(new BriefLogger(stream));
        // here is diff with previous checker
        checkerConfig.addAttribute("fileExtensions", "java,javax");

        // one more time on updated config
        verify(otherChecker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testForInvalidCheckImplementation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(BadJavaDocCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        try {
            verify(checkConfig, pathToEmptyFile, expected);
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().contains("isCommentNodesRequired"));
        }
    }

    @Test
    public void testProcessNonJavaFiles() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setTabWidth(1);
        treeWalker.configure(new DefaultConfiguration("default config"));
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
        final File file = new File("src/main/resources/checkstyle_packages.xml");
        treeWalker.processFiltered(file, new ArrayList<String>());
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createCheckConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
        treeWalker.setupChild(createCheckConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a {} ");
        treeWalker.processFiltered(file, lines);
    }

    @Test
    public void testProcessWithParserThrowable() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createCheckConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
        treeWalker.setupChild(createCheckConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" classD a {} ");

        try {
            treeWalker.processFiltered(file, lines);
        }
        catch (CheckstyleException exception) {
            assertTrue(exception.getMessage().contains(
                    "occurred during the analysis of file"));
        }
    }

    @Test
    public void testProcessWithRecognitionException() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createCheckConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
        treeWalker.setupChild(createCheckConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");

        try {
            treeWalker.processFiltered(file, lines);
        }
        catch (CheckstyleException exception) {
            assertTrue(exception.getMessage().contains(
                    "TokenStreamRecognitionException occurred during the analysis of file"));
        }
    }

    @Test
    public void testRequiredTokenIsNotInDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequiredTokenIsNotInDefaultsCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        try {
            verify(checkConfig, pathToEmptyFile, expected);
            fail();
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith("cannot initialize module"
                + " com.puppycrawl.tools.checkstyle.TreeWalker - Token \""
                + TokenTypes.ASSIGN + "\" from required"
                + " tokens was not found in default tokens list in check"));
        }
    }

    @Test
    public void testRequiredTokenIsEmptyIntArray() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequiredTokenIsEmptyIntArray.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        try {
            verify(checkConfig, pathToEmptyFile, expected);
        }
        catch (CheckstyleException ignored) {
            // unexpected
            fail();
        }
    }

    private static class BadJavaDocCheck extends Check {
        @Override
        public int[] getDefaultTokens() {
            return getAcceptableTokens();
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[]{TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public int[] getRequiredTokens() {
            return getAcceptableTokens();
        }
    }

    private static class RequiredTokenIsNotInDefaultsCheck extends Check {
        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.ASSIGN};
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.ANNOTATION};
        }

        @Override
        public int[] getAcceptableTokens() {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
    }

    private static class RequiredTokenIsEmptyIntArray extends Check {
        @Override
        public int[] getRequiredTokens() {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.ANNOTATION};
        }

        @Override
        public int[] getAcceptableTokens() {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
    }

}
