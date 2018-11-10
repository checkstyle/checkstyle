////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.internal.util.Checks;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;
import com.puppycrawl.tools.checkstyle.filters.SuppressionCommentFilter;
import com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TreeWalkerTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/treewalker";
    }

    @Test
    public void testProperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.java");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
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
                createModuleConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            final String content = "public class Main { public static final int k = 5 + 4; }";
            writer.write(content);
        }
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testAcceptableTokens()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF,"
                + "IMPORT");
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputTreeWalker.java"), expected);
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
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, pathToEmptyFile, expected);
    }

    @Test
    public void testWithCheckNotHavingTreeWalkerAsParent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(createChecker(checkConfig, ModuleCreationOption.IN_TREEWALKER),
                    temporaryFolder.newFile().getPath(), expected);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException exception) {
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains("TreeWalker is not allowed as a parent of"));
        }
    }

    @Test
    public void testSetupChildExceptions() {
        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);

        final Configuration config = new DefaultConfiguration("java.lang.String");
        try {
            treeWalker.setupChild(config);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Error message is not expected",
                    "TreeWalker is not allowed as a parent of java.lang.String Please review "
                            + "'Parent Module' section for this Check in web documentation if "
                            + "Check is standard.", ex.getMessage());
        }
    }

    @Test
    public void testSettersForParameters() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final DefaultConfiguration config = new DefaultConfiguration("default config");
        treeWalker.setTabWidth(1);
        treeWalker.configure(config);
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());

        assertEquals("Invalid setter result", 1,
                (int) Whitebox.getInternalState(treeWalker, "tabWidth"));
        assertEquals("Invalid configuration", config,
            Whitebox.getInternalState(treeWalker, "configuration"));
    }

    @Test
    public void testForInvalidCheckImplementation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BadJavaDocCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().contains("isCommentNodesRequired"));
        }
    }

    @Test
    public void testProcessNonJavaFiles() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.configure(new DefaultConfiguration("default config"));
        final DefaultConfiguration childConfig = createModuleConfig(JavadocParagraphCheck.class);
        treeWalker.setupChild(childConfig);
        final File file = new File("input.java");
        final List<String> lines =
            new ArrayList<>(Arrays.asList("package com.puppycrawl.tools.checkstyle;", "",
                "error public class InputTreeWalkerFileWithViolation {}"));
        final FileText fileText = new FileText(file, lines);
        try {
            treeWalker.processFiltered(file, fileText);
            fail("Exception expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid exception message",
                "MismatchedTokenException occurred while parsing file input.java.",
                ex.getMessage());
        }
    }

    @Test
    public void testProcessNonJavaFilesWithoutException() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setTabWidth(1);
        treeWalker.configure(new DefaultConfiguration("default config"));
        final File file = new File(getPath("InputTreeWalkerNotJava.xml"));
        final FileText fileText = new FileText(file, StandardCharsets.ISO_8859_1.name());
        treeWalker.processFiltered(file, fileText);
        final Collection<Checks> checks = Whitebox.getInternalState(treeWalker, "ordinaryChecks");
        assertTrue("No checks -> No parsing", checks.isEmpty());
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        final Checker checker = createChecker(checkConfig);
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        final File file = temporaryFolder.newFile("file.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checker, file.getPath(), expected);
    }

    @Test
    public void testProcessWithParserThrowable() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" classD a {} ");
        final FileText fileText = new FileText(file, lines);
        try {
            treeWalker.processFiltered(file, fileText);
            fail("Exception is expected");
        }
        catch (CheckstyleException exception) {
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains(
                    "occurred while parsing file"));
        }
    }

    @Test
    public void testProcessWithRecognitionException() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);
        try {
            treeWalker.processFiltered(file, fileText);
            fail("Exception is expected");
        }
        catch (CheckstyleException exception) {
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains(
                    "TokenStreamRecognitionException occurred while parsing file"));
        }
    }

    @Test
    public void testRequiredTokenIsNotInDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RequiredTokenIsNotInDefaultsCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("cannot initialize module"
                + " com.puppycrawl.tools.checkstyle.TreeWalker - Token \""
                + TokenTypes.ASSIGN + "\" from required"
                + " tokens was not found in default tokens list in check"));
        }
    }

    @Test
    public void testRequiredTokenIsEmptyIntArray() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RequiredTokenIsEmptyIntArray.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, pathToEmptyFile, expected);
    }

    @Test
    public void testBehaviourWithZeroChecks() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        // create file that should throw exception
        final File file = temporaryFolder.newFile("file.java");
        final FileText fileText = new FileText(file, new ArrayList<>());

        treeWalker.processFiltered(file, fileText);
        final Collection<Checks> checks = Whitebox.getInternalState(treeWalker, "ordinaryChecks");
        assertTrue("No checks -> No parsing", checks.isEmpty());
    }

    @Test
    public void testBehaviourWithOnlyOrdinaryChecks() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);

        try {
            treeWalker.processFiltered(file, fileText);
            fail("file is not compilable, exception is expected");
        }
        catch (CheckstyleException exception) {
            final String message =
                    "TokenStreamRecognitionException occurred while parsing file";
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains(message));
        }
    }

    @Test
    public void testBehaviourWithOnlyCommentChecks() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(CommentsIndentationCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(CommentsIndentationCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);

        try {
            treeWalker.processFiltered(file, fileText);
            fail("file is not compilable, exception is expected");
        }
        catch (CheckstyleException exception) {
            final String message =
                    "TokenStreamRecognitionException occurred while parsing file";
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains(message));
        }
    }

    @Test
    public void testBehaviourWithOrdinaryAndCommentChecks() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(TypeNameCheck.class));
        treeWalker.configure(createModuleConfig(CommentsIndentationCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(TypeNameCheck.class));
        treeWalker.setupChild(createModuleConfig(CommentsIndentationCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);

        try {
            treeWalker.processFiltered(file, fileText);
            fail("file is not compilable, exception is expected");
        }
        catch (CheckstyleException exception) {
            final String message =
                    "TokenStreamRecognitionException occurred while parsing file";
            assertTrue("Error message is unexpected",
                    exception.getMessage().contains(message));
        }
    }

    @Test
    public void testBehaviourWithChecksAndFilters() throws Exception {
        final DefaultConfiguration filterConfig =
                createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkCPP", "false");

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(createModuleConfig(MemberNameCheck.class));
        treeWalkerConfig.addChild(filterConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);

        final File file = new File(getPath("InputTreeWalkerSuppressionCommentFilter.java"));

        final String[] expected = {
            "9:17: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern", "P",
                    "^[a-z][a-zA-Z0-9]*$"),
            "4:17: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern", "I",
                    "^[a-z][a-zA-Z0-9]*$"),
        };

        verify(checkerConfig,
                file.getPath(),
                expected);
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() {
        final TreeWalker treeWalker = new TreeWalker();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        treeWalker.setClassLoader(contextClassLoader);
        treeWalker.setSeverity("error");
        treeWalker.setTabWidth(100);
        treeWalker.finishLocalSetup();

        final Context context = Whitebox.getInternalState(treeWalker, "childContext");
        assertEquals("Classloader object differs from expected",
                contextClassLoader, context.get("classLoader"));
        assertEquals("Severity differs from expected",
                "error", context.get("severity"));
        assertEquals("Tab width differs from expected",
                String.valueOf(100), context.get("tabWidth"));
    }

    @Test
    public void testCheckInitIsCalledInTreeWalker() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyInitCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
        assertTrue("Init was not called", VerifyInitCheck.isInitWasCalled());
    }

    @Test
    public void testCheckDestroyIsCalledInTreeWalker() throws Exception {
        VerifyDestroyCheck.resetDestroyWasCalled();
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyDestroyCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
        assertTrue("Destroy was not called", VerifyDestroyCheck.isDestroyWasCalled());
    }

    @Test
    public void testCommentCheckDestroyIsCalledInTreeWalker() throws Exception {
        VerifyDestroyCheck.resetDestroyWasCalled();
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyDestroyCommentCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getPath(), expected);
        assertTrue("Destroy was not called", VerifyDestroyCheck.isDestroyWasCalled());
    }

    @Test
    public void testCacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionXpathFilter.class);
        filterConfig.addAttribute("file", getPath("InputTreeWalkerSuppressionXpathFilter.xml"));
        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(filterConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String filePath = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, filePath, expected);
        // One more time to use cache.
        verify(checkerConfig, filePath, expected);

        assertTrue("External resource is not present in cache",
                new String(Files.readAllBytes(cacheFile.toPath()),
                        StandardCharsets.UTF_8).contains(
                                "InputTreeWalkerSuppressionXpathFilter.xml"));
    }

    @Test
    public void testTreeWalkerFilterAbsolutePath() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionXpathFilter.class);
        filterConfig.addAttribute("file",
                getPath("InputTreeWalkerSuppressionXpathFilterAbsolute.xml"));
        final DefaultConfiguration checkConfig = createModuleConfig(LeftCurlyCheck.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(filterConfig);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);

        // test is only valid when relative paths are given
        final String filePath = "src/test/resources/" + getPackageLocation()
                + "/InputTreeWalkerSuppressionXpathFilterAbsolute.java";
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, filePath, expected);
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
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return getDefaultTokens();
        }

        @Override
        public int[] getRequiredTokens() {
            return getDefaultTokens();
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

    private static class VerifyDestroyCheck extends AbstractCheck {

        private static boolean destroyWasCalled;

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return getDefaultTokens();
        }

        @Override
        public int[] getRequiredTokens() {
            return getDefaultTokens();
        }

        @Override
        public void destroy() {
            super.destroy();
            destroyWasCalled = true;
        }

        public static void resetDestroyWasCalled() {
            destroyWasCalled = false;
        }

        public static boolean isDestroyWasCalled() {
            return destroyWasCalled;
        }

    }

    private static class VerifyDestroyCommentCheck extends VerifyDestroyCheck {

        @Override
        public boolean isCommentNodesRequired() {
            return true;
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
            return CommonUtil.EMPTY_INT_ARRAY;
        }

    }

    private static class RequiredTokenIsEmptyIntArray extends AbstractCheck {

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.ANNOTATION};
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

    }

}
