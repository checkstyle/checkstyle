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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.Checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.NoCodeInFileCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck;
import com.puppycrawl.tools.checkstyle.filters.SuppressWithNearbyCommentFilter;
import com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * TreeWalkerTest.
 *
 * @noinspection ClassWithTooManyDependencies because we are less strict with tests.
 * @noinspectionreason ClassWithTooManyDependencies - complex tests require a
 *      large number of imports
 */
public class TreeWalkerTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/treewalker";
    }

    @Test
    public void testProperFileExtension() throws Exception {
        final String path = getPath("InputTreeWalkerProperFileExtension.java");
        final String[] expected = {
            "10:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "k", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verifyWithInlineConfigParserTwice(path, expected);
    }

    /**
     * This test is needed for 100% coverage.
     * The Pitest reports some conditions as redundant, for example:
     * <pre>
     *     if (!collection.isEmpty()) { // This may be omitted.
     *         Object value = doSomeHardJob();
     *         for (Item item : collection) {
     *             item.accept(value);
     *         }
     *     }
     * </pre>
     * But we really want to avoid calls to {@code doSomeHardJob} method.
     * To make this condition mandatory, we need to broke one branch.
     * In this case, mocking {@code TreeWalkerAuditEvent} will cause
     * {@code getFilteredViolations} to fail. This prevents the condition
     * <pre>
     *     if (filters.isEmpty())
     * </pre>
     * in {@link TreeWalker#processFiltered(File, FileText)} to survive with Pitest mutations.
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void testNoAuditEventsWithoutFilters() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(OneTopLevelClassCheck.class,
                    OneTopLevelClassCheck.MSG_KEY, "InputTreeWalkerInner"),
        };
        try (MockedConstruction<TreeWalkerAuditEvent> mocked =
                 Mockito.mockConstruction(TreeWalkerAuditEvent.class, (mock, context) -> {
                     throw new CheckstyleException("No audit events expected");
                 })) {
            verifyWithInlineConfigParserTwice(getPath("InputTreeWalker.java"), expected);
        }
    }

    /**
     * This test is needed for 100% coverage. The method {@link Mockito#mockStatic} is used to
     * ensure that the {@code if (!ordinaryChecks.isEmpty())} condition cannot be removed.
     */
    @Test
    public void testConditionRequiredWithoutOrdinaryChecks() throws Exception {
        final String[] expected = {
            "7:5: " + getCheckMessage(JavadocParagraphCheck.class,
                    JavadocParagraphCheck.MSG_REDUNDANT_PARAGRAPH),
        };
        final String path = getPath("InputTreeWalkerJavadoc.java");
        final DetailAST mockAst = mock();
        final DetailAST realAst = JavaParser.parseFile(new File(path),
                JavaParser.Options.WITH_COMMENTS);
        // Ensure that there is no calls to walk(..., AstState.ORDINARY)
        doThrow(IllegalStateException.class).when(mockAst).getFirstChild();
        try (MockedStatic<JavaParser> parser = Mockito.mockStatic(JavaParser.class)) {
            parser.when(() -> JavaParser.parse(any(FileContents.class))).thenReturn(mockAst);
            // This will re-enable walk(..., AstState.WITH_COMMENTS)
            parser.when(() -> JavaParser.appendHiddenCommentNodes(mockAst)).thenReturn(realAst);

            verifyWithInlineConfigParserTwice(path, expected);
        }
    }

    /**
     * This test is needed for 100% coverage. The method {@link Mockito#mockStatic} is used to
     * ensure that the {@code if (!commentChecks.isEmpty())} condition cannot be removed.
     */
    @Test
    public void testConditionRequiredWithoutCommentChecks() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(OneTopLevelClassCheck.class,
                    OneTopLevelClassCheck.MSG_KEY, "InputTreeWalkerInner"),
        };
        try (MockedStatic<JavaParser> parser =
                     Mockito.mockStatic(JavaParser.class, CALLS_REAL_METHODS)) {
            // Ensure that there is no calls to walk(..., AstState.WITH_COMMENTS)
            parser.when(() -> JavaParser.appendHiddenCommentNodes(any(DetailAST.class)))
                    .thenThrow(IllegalStateException.class);

            verifyWithInlineConfigParserTwice(getPath("InputTreeWalker.java"), expected);
        }
    }

    @Test
    public void testImproperFileExtension() throws Exception {
        final String regularFilePath = getPath("InputTreeWalkerImproperFileExtension.java");
        final File originalFile = new File(regularFilePath);
        final File tempFile = new File(temporaryFolder, "file.pdf");
        Files.copy(originalFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(tempFile.getPath(), expected);
    }

    @Test
    public void testAcceptableTokens()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("tokens", "VARIABLE_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF,"
                + "IMPORT");
        try {
            execute(checkConfig, getPath("InputTreeWalker.java"));
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final String errorMsg = ex.getMessage();
            final Pattern expected = Pattern.compile(Pattern.quote("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.TreeWalker - Token ")
                    + "\"(ENUM_DEF|CLASS_DEF|METHOD_DEF|IMPORT)\""
                    + Pattern.quote(" was not found in Acceptable tokens list in check"
                    + " com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck"));

            final Matcher errorMsgMatcher = expected.matcher(errorMsg);
            assertWithMessage("Failure for: " + errorMsg)
                    .that(errorMsgMatcher.matches())
                    .isTrue();
        }
    }

    @Test
    public void testOnEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".java";
        final File emptyFile = new File(temporaryFolder, uniqueFileName);
        emptyFile.createNewFile();
        execute(checkConfig, emptyFile.getPath());
        final long fileSize = Files.size(emptyFile.toPath());
        assertWithMessage("File should be empty")
                .that(fileSize)
                .isEqualTo(0);
    }

    @Test
    public void testWithCheckNotHavingTreeWalkerAsParent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);

        try {
            final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
            final File filePath = new File(temporaryFolder, uniqueFileName);
            execute(createTreeWalkerConfig(checkConfig), filePath.toString());
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException exception) {
            assertWithMessage("Error message is unexpected")
                    .that(exception.getMessage())
                    .contains("TreeWalker is not allowed as a parent of");
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
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("TreeWalker is not allowed as a parent of java.lang.String "
                    + "Please review 'Parent Module' section for this Check in "
                    + "web documentation if Check is standard.");
        }
    }

    @Test
    public void testSettersForParameters() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final DefaultConfiguration config = new DefaultConfiguration("default config");
        treeWalker.setTabWidth(1);
        treeWalker.configure(config);

        final int tabWidth = TestUtil.getInternalState(treeWalker, "tabWidth");
        assertWithMessage("Invalid setter result")
            .that(tabWidth)
            .isEqualTo(1);
        final Object configuration = TestUtil.getInternalState(treeWalker, "configuration");
        assertWithMessage("Invalid configuration")
            .that(configuration)
            .isEqualTo(config);
    }

    @Test
    public void testForInvalidCheckImplementation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BadJavaDocCheck.class);
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".java";
        final File pathToEmptyFile = new File(temporaryFolder, uniqueFileName);

        try {
            execute(checkConfig, pathToEmptyFile.toString());
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle."
                            + "TreeWalker - Check 'com.puppycrawl.tools.checkstyle."
                            + "TreeWalkerTest$BadJavaDocCheck' waits for comment type token "
                            + "('SINGLE_LINE_COMMENT') and should override "
                            + "'isCommentNodesRequired()' method to return 'true'");
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .contains("isCommentNodesRequired");
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
        treeWalker.setFileContents(new FileContents(fileText));
        try {
            treeWalker.processFiltered(file, fileText);
            assertWithMessage("Exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("IllegalStateException occurred while parsing file input.java.");
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
        final Collection<Checks> checks = TestUtil.getInternalState(treeWalker, "ordinaryChecks");
        assertWithMessage("No checks -> No parsing")
            .that(checks)
            .isEmpty();
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final String path = getPath("InputTreeWalkerWithCacheWithNoViolation.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(path, expected);
    }

    @Test
    public void testProcessWithParserThrowable() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.configure(createModuleConfig(TypeNameCheck.class));
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setupChild(createModuleConfig(TypeNameCheck.class));
        final File file = new File(temporaryFolder, "file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" classD a {} ");
        final FileText fileText = new FileText(file, lines);
        treeWalker.setFileContents(new FileContents(fileText));
        try {
            treeWalker.processFiltered(file, fileText);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exception) {
            assertWithMessage("Error message is unexpected")
                    .that(exception.getMessage())
                    .contains("occurred while parsing file");
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
        final File file = new File(temporaryFolder, "file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);
        treeWalker.setFileContents(new FileContents(fileText));
        try {
            treeWalker.processFiltered(file, fileText);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exception) {
            assertWithMessage("Error message is unexpected")
                    .that(exception.getMessage())
                    .contains("IllegalStateException occurred while parsing file");
        }
    }

    @Test
    public void testRequiredTokenIsEmptyIntArray() throws Exception {
        final File file = new File(temporaryFolder, "file.java");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            final String configComment = "/*\n"
                    + "com.puppycrawl.tools.checkstyle.TreeWalkerTest"
                    + "$RequiredTokenIsEmptyIntArray\n\n"
                    + "*/";
            writer.write(configComment);
        }
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(file.getPath(), expected);
    }

    @Test
    public void testBehaviourWithZeroChecks() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        // create file that should throw exception
        final File file = new File(temporaryFolder, "file.java");
        final FileText fileText = new FileText(file, new ArrayList<>());

        treeWalker.processFiltered(file, fileText);
        final Collection<Checks> checks = TestUtil.getInternalState(treeWalker, "ordinaryChecks");
        assertWithMessage("No checks -> No parsing")
            .that(checks)
            .isEmpty();
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
        final File file = new File(temporaryFolder, "file.java");
        final List<String> lines = new ArrayList<>();
        lines.add(" class a%$# {} ");
        final FileText fileText = new FileText(file, lines);
        treeWalker.setFileContents(new FileContents(fileText));

        try {
            treeWalker.processFiltered(file, fileText);
            assertWithMessage("file is not compilable, exception is expected").fail();
        }
        catch (CheckstyleException exception) {
            final String message = "IllegalStateException occurred while parsing file";
            assertWithMessage("Error message is unexpected")
                    .that(exception.getMessage())
                    .contains(message);
        }
    }

    @Test
    public void testSetupChild() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalker.setModuleFactory(factory);
        treeWalker.setTabWidth(99);
        treeWalker.finishLocalSetup();

        final Configuration config = new DefaultConfiguration(
                XpathFileGeneratorAstFilter.class.getName());

        treeWalker.setupChild(config);

        final Set<TreeWalkerFilter> filters = TestUtil.getInternalState(treeWalker, "filters");
        final int tabWidth = TestUtil.getInternalState(filters.iterator().next(), "tabWidth");

        assertWithMessage("expected tab width")
            .that(tabWidth)
            .isEqualTo(99);
    }

    @Test
    public void testBehaviourWithChecksAndFilters() throws Exception {

        final String[] expected = {
            "17:17: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern", "P",
                    "^[a-z][a-zA-Z0-9]*$"),
            "12:17: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern", "I",
                    "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParserTwice(
                getPath("InputTreeWalkerSuppressionCommentFilter.java"),
                expected);
    }

    @Test
    public void testMultiCheckOrder() throws Exception {

        final String[] expected = {
            "13:9: " + getCheckMessage(WhitespaceAfterCheck.class, "ws.notFollowed", "if"),
            "13:9: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "if"),
        };

        verifyWithInlineConfigParserTwice(
                getPath("InputTreeWalkerMultiCheckOrder.java"),
                expected);
    }

    @Test
    public void testMultiCheckOfSameTypeNoIdResultsInOrderingByHash() throws Exception {

        final String[] expected = {
            "15:28: " + getCheckMessage(ParameterNameCheck.class,
                    "name.invalidPattern", "V2", "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"),
            "17:25: " + getCheckMessage(ParameterNameCheck.class,
                    "name.invalidPattern", "b", "^[a-z][a-z0-9][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParserTwice(
                getPath("InputTreeWalkerMultiCheckOrder2.java"),
                expected);
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setSeverity("error");
        treeWalker.setTabWidth(100);
        treeWalker.finishLocalSetup();

        final Context context = TestUtil.getInternalState(treeWalker, "childContext");
        assertWithMessage("Severity differs from expected")
            .that(context.get("severity"))
            .isEqualTo("error");
        assertWithMessage("Tab width differs from expected")
            .that(context.get("tabWidth"))
            .isEqualTo(String.valueOf(100));
    }

    @Test
    public void testCheckInitIsCalledInTreeWalker() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyInitCheck.class);
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".pdf";
        final File file = new File(temporaryFolder, uniqueFileName);
        execute(checkConfig, file.getPath());
        assertWithMessage("Init was not called")
                .that(VerifyInitCheck.isInitWasCalled())
                .isTrue();
    }

    @Test
    public void testCheckDestroyIsCalledInTreeWalker() throws Exception {
        VerifyDestroyCheck.resetDestroyWasCalled();
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyDestroyCheck.class);
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".pdf";
        final File file = new File(temporaryFolder, uniqueFileName);
        execute(checkConfig, file.getPath());
        assertWithMessage("Destroy was not called")
                .that(VerifyDestroyCheck.isDestroyWasCalled())
                .isTrue();
    }

    @Test
    public void testCommentCheckDestroyIsCalledInTreeWalker() throws Exception {
        VerifyDestroyCheck.resetDestroyWasCalled();
        final DefaultConfiguration checkConfig =
                createModuleConfig(VerifyDestroyCommentCheck.class);
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".pdf";
        final File file = new File(temporaryFolder, uniqueFileName);
        execute(checkConfig, file.getPath());
        assertWithMessage("Destroy was not called")
                .that(VerifyDestroyCheck.isDestroyWasCalled())
                .isTrue();
    }

    @Test
    public void testCacheWhenFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionXpathFilter.class);
        filterConfig.addProperty("file", getPath("InputTreeWalkerSuppressionXpathFilter.xml"));
        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(filterConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        final String uniqueFileName1 = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName1);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String uniqueFileName2 = "file_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName2);

        execute(checkerConfig, filePath.toString());
        // One more time to use cache.
        execute(checkerConfig, filePath.toString());

        assertWithMessage("External resource is not present in cache")
                .that(Files.readString(cacheFile.toPath()))
                .contains("InputTreeWalkerSuppressionXpathFilter.xml");
    }

    @Test
    public void testTreeWalkerFilterAbsolutePath() throws Exception {
        // test is only valid when relative paths are given
        final String filePath = "src/test/resources/" + getPackageLocation()
                + "/InputTreeWalkerSuppressionXpathFilterAbsolute.java";

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(filePath, expected);
    }

    @Test
    public void testExternalResourceFiltersWithNoExternalResource() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyStatementCheck.class);
        final DefaultConfiguration filterConfig =
                createModuleConfig(SuppressWithNearbyCommentFilter.class);
        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);
        treeWalkerConfig.addChild(filterConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        final String uniqueFileName1 = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName1);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());
        final String uniqueFileName2 = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName2);

        execute(checkerConfig, filePath.toString());

        final long cacheSize = Files.size(cacheFile.toPath());
        assertWithMessage("cacheFile should not be empty")
                .that(cacheSize)
                .isNotEqualTo(0);
    }

    /**
     * This test is checking that Checks execution ordered by name.
     *
     * @throws Exception if file is not found
     */
    @Test
    public void testOrderOfCheckExecution() throws Exception {

        final DefaultConfiguration configuration1 = createModuleConfig(AaCheck.class);
        configuration1.addProperty("id", "2");
        final DefaultConfiguration configuration2 = createModuleConfig(BbCheck.class);
        configuration2.addProperty("id", "1");

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(configuration2);
        treeWalkerConfig.addChild(configuration1);

        final List<File> files =
                Collections.singletonList(new File(getPath("InputTreeWalker2.java")));
        final Checker checker = createChecker(treeWalkerConfig);

        try {
            checker.process(files);
            assertWithMessage("exception is expected").fail();
        }
        catch (CheckstyleException exception) {
            assertWithMessage("wrong order of Check executions")
                    .that(exception.getCause().getMessage())
                    .isEqualTo(AaCheck.class.toString());
        }
    }

    @Test
    public void testSkipFileOnJavaParseExceptionTrue() throws Exception {
        final DefaultConfiguration config = createModuleConfig(TreeWalker.class);
        config.addProperty("skipFileOnJavaParseException", "true");
        config.addProperty("javaParseExceptionSeverity", "ignore");
        config.addChild(createModuleConfig(ConstantNameCheck.class));

        final File[] files = {
            new File(getNonCompilablePath("InputTreeWalkerSkipParsingException.java")),
            new File(getPath("InputTreeWalkerProperFileExtension.java")),
            new File(getNonCompilablePath("InputTreeWalkerSkipParsingException2.java")),
        };

        final Checker checker = createChecker(config);
        final Map<String, List<String>> expectedViolation = new HashMap<>();
        expectedViolation.put(getPath("InputTreeWalkerProperFileExtension.java"),
                Collections.singletonList(
                        "10:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "k", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$")));
        verify(checker, files, expectedViolation);
    }

    /**
     * This test method is not using the regular {@code verify} methods
     * because it is testing the behavior of {@code TreeWalker} when it should
     * not skip files with parse exception. Instead, it should throw {@code exception}.
     * The test verifies this behavior by attempting to process files
     * that are known to cause a Java parse exception and asserting that
     * the expected {@code exception} is indeed thrown.
     *
     */
    @Test
    public void testSkipFileOnJavaParseExceptionFalse() throws Exception {
        final DefaultConfiguration config = createModuleConfig(TreeWalker.class);
        config.addProperty("skipFileOnJavaParseException", "false");
        config.addChild(createModuleConfig(ConstantNameCheck.class));

        final String[] files = {
            getNonCompilablePath("InputTreeWalkerSkipParsingException2.java"),
            getPath("InputTreeWalkerProperFileExtension.java"),
            getNonCompilablePath("InputTreeWalkerSkipParsingException.java"),
        };
        final Exception ex = TestUtil.getExpectedThrowable(CheckstyleException.class,
                () -> execute(config, files),
                "Exception is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .contains("Exception was thrown while processing");
    }

    @Test
    public void testSkipFileOnJavaParseExceptionConfigSeverityIgnore() throws Exception {
        final String path =
                getNonCompilablePath(
                        "InputTreeWalkerSkipParsingExceptionConfigSeverityIgnore.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineXmlConfig(path, expected);
    }

    @Test
    public void testSkipFileOnJavaParseExceptionConfigSeverityDefault() throws Exception {
        final String path =
                getNonCompilablePath(
                        "InputTreeWalkerSkipParsingExceptionConfigSeverityDefault.java");
        final String[] expected = {
            "1: " + getCheckMessage(TreeWalker.PARSE_EXCEPTION_MSG, "IllegalStateException")
                  + " occurred while parsing file " + path + ".",
        };
        verifyWithInlineXmlConfig(path, expected);
    }

    @Test
    public void testSkipFileOnJavaParseExceptionSkipChecks() throws Exception {
        final DefaultConfiguration config = createModuleConfig(TreeWalker.class);
        config.addProperty("skipFileOnJavaParseException", "true");
        config.addProperty("javaParseExceptionSeverity", "ignore");
        config.addChild(createModuleConfig(NoCodeInFileCheck.class));

        final Checker checker = createChecker(config);

        final File[] files = {
            new File(getNonCompilablePath("InputTreeWalkerSkipParsingException.java")),
            new File(getPath("InputTreeWalkerProperFileExtension.java")),
            new File(getNonCompilablePath("InputTreeWalkerSkipParsingException2.java")),
        };
        final Map<String, List<String>> expectedViolation = new HashMap<>();
        expectedViolation.put(getPath("InputTreeWalkerProperFileExtension.java"),
                new ArrayList<>());

        verify(checker, files, expectedViolation);
    }

    @Test
    public void testJavaParseExceptionSeverityDefaultError() throws Exception {
        final DefaultConfiguration config = createModuleConfig(TreeWalker.class);
        config.addProperty("skipFileOnJavaParseException", "true");
        config.addChild(createModuleConfig(NoCodeInFileCheck.class));

        final Checker checker = createChecker(config);

        final File[] files = {
            new File(getNonCompilablePath("InputTreeWalkerSkipParsingException.java")),
            new File(getPath("InputTreeWalkerProperFileExtension.java")),
        };

        final Map<String, List<String>> expectedViolation = new HashMap<>();

        expectedViolation.put(getPath("InputTreeWalkerProperFileExtension.java"),
                new ArrayList<>());
        expectedViolation.put(getNonCompilablePath("InputTreeWalkerSkipParsingException.java"),
                List.of("1: Java specific (TreeWalker-based) modules are skipped due to an "
                        + "exception during parsing - "
                        + "IllegalStateException occurred while parsing file "
                        + getNonCompilablePath("InputTreeWalkerSkipParsingException.java") + "."));

        verify(checker, files, expectedViolation);
    }

    @Test
    public void testThenComparingWithMethodReference() {
        final List<TestCheck> checks = Arrays.asList(
                new TestCheck("check2"),
                new TestCheck("check1"),
                new TestCheck("check3")
        );

        checks.sort(Comparator
                .comparing(TestCheck::getPriority)
                .thenComparing(TestCheck::getCustomId));

        Assertions.assertEquals("check1", checks.get(0).getCustomId(),
                "First element should be 'check1'");
        Assertions.assertEquals("check2", checks.get(1).getCustomId(),
                "Second element should be 'check2'");
        Assertions.assertEquals("check3", checks.get(2).getCustomId(),
                "Third element should be 'check3'");
    }

    public static class TestCheck extends AbstractCheck {
        private final String customId;
        private final int priority;

        public TestCheck(String id) {
            customId = Objects.requireNonNullElse(id, "");
            priority = 1;
        }

        /**
         * Retrieves the custom ID for this check.
         * This replaces getId() since getId() is final and cannot be overridden.
         *
         * @return the custom ID of this check.
         */
        public String getCustomId() {
            return customId;
        }

        /**
         * Returns the priority level of this check.
         *
         * @return the priority level.
         */
        public int getPriority() {
            return priority;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[0];
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[0];
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[0];
        }

        @Override
        public void visitToken(DetailAST ast) {
            // No-op
        }
    }

    public static class BadJavaDocCheck extends AbstractCheck {

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

    public static class VerifyInitCheck extends AbstractCheck {

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

    public static class VerifyDestroyCheck extends AbstractCheck {

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

    public static class VerifyDestroyCommentCheck extends VerifyDestroyCheck {

        @Override
        public boolean isCommentNodesRequired() {
            return true;
        }

    }

    public static class AaCheck extends AbstractCheck {

        @Override
        public int[] getDefaultTokens() {
            return new int[0];
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[0];
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[0];
        }

        @Override
        public void beginTree(DetailAST rootAST) {
            throw new IllegalStateException(AaCheck.class.toString());
        }

    }

    public static class BbCheck extends AbstractCheck {

        @Override
        public int[] getDefaultTokens() {
            return new int[0];
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[0];
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[0];
        }

        @Override
        public void beginTree(DetailAST rootAST) {
            throw new IllegalStateException(BbCheck.class.toString());
        }

    }

    public static class RequiredTokenIsEmptyIntArray extends AbstractCheck {

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
