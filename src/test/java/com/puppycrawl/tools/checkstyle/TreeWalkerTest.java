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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TreeWalker.class)
public class TreeWalkerTest extends BaseCheckTestSupport {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testProperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.java");
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            final String content = "public class Main { public static final int k = 5 + 4; }";
            writer.write(content);
        }
        final String[] expected1 = {
            "1:45: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "k", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verify(checkConfig, file.getPath(), expected1);
    }

    @Test
    public void testImproperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            final String content = "public class Main { public static final int k = 5 + 4; }";
            writer.write(content);
        }
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testAcceptableTokens()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF,"
                + "IMPORT");
        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputMain.java"), expected);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            final String errorMsg = ex.getMessage();
            final Pattern expected = Pattern.compile(Pattern.quote("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.TreeWalker - Token ")
                    + "\"(ENUM_DEF|CLASS_DEF|METHOD_DEF|IMPORT)\""
                    + Pattern.quote(" was not found in Acceptable tokens list in check"
                    + " com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck"));

            final Matcher errorMsgMatcher = expected.matcher(errorMsg);
            assertTrue("Failure for: " + errorMsg, errorMsgMatcher.matches());
        }
    }

    @Test
    public void testOnEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, pathToEmptyFile, expected);
    }

    @Test
    public void testWithCheckNotHavingTreeWalkerAsParent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, temporaryFolder.newFile().getPath(), expected);
            fail("CheckstyleException is expected");
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
    public void testForInvalidCheckImplementation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(BadJavaDocCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
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
        final File file = new File("src/main/resources/checkstyle_packages.xml");
        treeWalker.processFiltered(file, new ArrayList<>());
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createCheckConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
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
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
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
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
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

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
            fail("CheckstyleException is expected");
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

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
        }
        catch (CheckstyleException ignored) {
            // unexpected
            fail("CheckstyleException is NOT expected");
        }
    }

    @Test
    public void testBehaviourWithZeroChecks() throws Exception {
        final String errMsg = "No checks -> No parsing";
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        mockStatic(TreeWalker.class);
        when(TreeWalker.parse(any(FileContents.class)))
                .thenThrow(new IllegalStateException(errMsg));
        doNothing().when(treeWalkerSpy, "walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        treeWalkerSpy.processFiltered(temporaryFolder.newFile("file.java"), new ArrayList<>());
        verifyPrivate(treeWalkerSpy, never()).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
    }

    @Test
    public void testBehaviourWithOnlyOrdinaryChecks() throws Exception {
        final String errMsg = "No comment processing checks -> "
                + "No calls to TreeWalker.appendHiddenCommentNodes";
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalkerSpy.configure(createCheckConfig(TypeNameCheck.class));
        treeWalkerSpy.setModuleFactory(factory);
        treeWalkerSpy.setupChild(createCheckConfig(TypeNameCheck.class));
        spy(TreeWalker.class);
        doNothing().when(treeWalkerSpy, "walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        when(TreeWalker.class, "appendHiddenCommentNodes", any(DetailAST.class))
                .thenThrow(new IllegalStateException(errMsg));
        treeWalkerSpy.processFiltered(temporaryFolder.newFile("file.java"), new ArrayList<>());
        verifyPrivate(treeWalkerSpy, times(1)).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
    }

    @Test
    public void testBehaviourWithOnlyCommentChecks() throws Exception {
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalkerSpy.configure(createCheckConfig(CommentsIndentationCheck.class));
        treeWalkerSpy.setModuleFactory(factory);
        treeWalkerSpy.setupChild(createCheckConfig(CommentsIndentationCheck.class));
        spy(TreeWalker.class);
        doNothing().when(treeWalkerSpy, "walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        treeWalkerSpy.processFiltered(temporaryFolder.newFile("file.java"), new ArrayList<>());
        verifyPrivate(TreeWalker.class, times(1))
                .invoke("appendHiddenCommentNodes", any(DetailAST.class));
        verifyPrivate(treeWalkerSpy, times(1)).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
    }

    @Test
    public void testBehaviourWithOrdinaryAndCommentChecks() throws Exception {
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalkerSpy.configure(new DefaultConfiguration("TreeWalkerTest"));
        treeWalkerSpy.setModuleFactory(factory);
        treeWalkerSpy.setupChild(createCheckConfig(CommentsIndentationCheck.class));
        treeWalkerSpy.setupChild(createCheckConfig(TypeNameCheck.class));
        spy(TreeWalker.class);
        doNothing().when(treeWalkerSpy, "walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        treeWalkerSpy.processFiltered(temporaryFolder.newFile("file.java"), new ArrayList<>());
        verifyPrivate(TreeWalker.class, times(1))
                .invoke("appendHiddenCommentNodes", any(DetailAST.class));
        verifyPrivate(treeWalkerSpy, times(2)).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        verifyStatic(times(1));
        TreeWalker.parse(any(FileContents.class));
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        treeWalker.setClassLoader(contextClassLoader);
        treeWalker.setSeverity("error");
        treeWalker.setTabWidth(100);
        treeWalker.finishLocalSetup();

        final Context context = (Context) Whitebox.getInternalState(treeWalker, "childContext");
        assertEquals(contextClassLoader, context.get("classLoader"));
        assertEquals("error", context.get("severity"));
        assertEquals(String.valueOf(100), context.get("tabWidth"));
    }

    @Test
    public void testCheckInitIsCalledInTreeWalker() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(VerifyInitCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
        assertTrue(VerifyInitCheck.isInitWasCalled());
    }

    private static class BadJavaDocCheck extends AbstractCheck {
        @Override
        public int[] getDefaultTokens() {
            return getAcceptableTokens();
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public int[] getRequiredTokens() {
            return getAcceptableTokens();
        }
    }

    private static class VerifyInitCheck extends AbstractCheck {
        private static boolean initWasCalled;

        @Override
        public int[] getDefaultTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public void init() {
            super.init();
            initWasCalled = true;
        }

        public static boolean isInitWasCalled() {
            return initWasCalled;
        }
    }

    private static class RequiredTokenIsNotInDefaultsCheck extends AbstractCheck {
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
            return CommonUtils.EMPTY_INT_ARRAY;
        }
    }

    private static class RequiredTokenIsEmptyIntArray extends AbstractCheck {
        @Override
        public int[] getRequiredTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.ANNOTATION};
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }
    }
}
