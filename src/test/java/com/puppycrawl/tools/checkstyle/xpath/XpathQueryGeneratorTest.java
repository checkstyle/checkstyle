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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class XpathQueryGeneratorTest extends AbstractModuleTestSupport {

    private static final int DEFAULT_TAB_WIDTH = 4;

    private static DetailAST rootAst;

    private static FileText fileText;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathquerygenerator";
    }

    @BeforeEach
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathQueryGenerator.java"));
        fileText = new FileText(file,
                StandardCharsets.UTF_8.name());
        rootAst = JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testClassDef() {
        final int lineNumber = 12;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/MODIFIERS/LITERAL_PUBLIC");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testMethodDef() {
        final int lineNumber = 45;
        final int columnNumber = 5;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/MODIFIERS/LITERAL_PUBLIC");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testVariableDef() {
        final int lineNumber = 53;
        final int columnNumber = 13;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/TYPE/LITERAL_SHORT");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testLcurly() {
        final int lineNumber = 37;
        final int columnNumber = 20;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/LCURLY");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testRcurly() {
        final int lineNumber = 25;
        final int columnNumber = 5;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/INSTANCE_INIT/SLIST/RCURLY");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testExpr() {
        final int lineNumber = 17;
        final int columnNumber = 50;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='mUse4']]/ASSIGN/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='mUse4']]/ASSIGN/EXPR/DOT");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testLparen() {
        final int lineNumber = 45;
        final int columnNumber = 31;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/LPAREN");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testEmpty() {
        final int lineNumber = 300;
        final int columnNumber = 300;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        assertWithMessage("Result should be empty")
            .that(actual)
            .isEmpty();
    }

    @Test
    public void testPackage() {
        final int lineNumber = 2;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testImport() {
        final int lineNumber = 5;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='File']]");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testMethodParams() {
        final int lineNumber = 72;
        final int columnNumber = 30;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/TYPE[./IDENT[@text='String']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/TYPE/IDENT[@text='String']");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testSwitch() {
        final int lineNumber = 37;
        final int columnNumber = 9;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testSwitchCase() {
        final int lineNumber = 38;
        final int columnNumber = 13;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/CASE_GROUP[1]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testVariableStringLiteral() {
        final int lineNumber = 47;
        final int columnNumber = 26;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='HelloWorld']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='HelloWorld']");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testComma() {
        final int lineNumber = 66;
        final int columnNumber = 36;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='foo']]/SLIST/LITERAL_FOR/FOR_ITERATOR/ELIST/COMMA");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testLiteralVoid() {
        final int lineNumber = 65;
        final int columnNumber = 12;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/TYPE/LITERAL_VOID");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testFirstImport() {
        final int lineNumber = 4;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='JToolBar']]");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testLastImport() {
        final int lineNumber = 8;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='Iterator']]");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testImportByText() {
        final int lineNumber = 4;
        final int columnNumber = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT/DOT[./IDENT[@text='JToolBar']]/DOT/IDENT[@text='javax']");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testIdent() {
        final int lineNumber = 12;
        final int columnNumber = 14;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF/IDENT[@text='InputXpathQueryGenerator']");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTabWidthBeforeMethodDef() throws Exception {
        final File testFile = new File(getPath(
                "InputXpathQueryGeneratorTabWidth.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int lineNumber = 4;
        final int columnNumber = 13;
        final int tabWidth = 4;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst, lineNumber,
                columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS/LITERAL_PUBLIC");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTabWidthAfterVoidLiteral() throws Exception {
        final File testFile = new File(getPath(
                "InputXpathQueryGeneratorTabWidth.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int lineNumber = 8;
        final int columnNumber = 41;
        final int tabWidth = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst, lineNumber,
                columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/TYPE/LITERAL_VOID");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTabWidthBeforeSlist() throws Exception {
        final File testFile = new File(getPath("InputXpathQueryGeneratorTabWidth.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int lineNumber = 12;
        final int columnNumber = 57;
        final int tabWidth = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst, lineNumber,
                columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='tabAfterMe']]/SLIST");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTabWidthEndOfLine() throws Exception {
        final File testFile = new File(getPath("InputXpathQueryGeneratorTabWidth.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int lineNumber = 16;
        final int columnNumber = 58;
        final int tabWidth = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst, lineNumber,
                columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='endLineTab']]/SEMI");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testClassDefWithTokenType() {
        final int lineNumber = 12;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, TokenTypes.CLASS_DEF, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testConstructorWithTreeWalkerAuditEvent() {
        final Violation violation = new Violation(12, 1, "messages.properties", null,
                null, null, null, null, null);
        final TreeWalkerAuditEvent event = new TreeWalkerAuditEvent(new FileContents(fileText),
                "InputXpathQueryGenerator", violation, rootAst);
        final XpathQueryGenerator queryGenerator =
                new XpathQueryGenerator(event, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS"
                    + "/LITERAL_PUBLIC");
        assertWithMessage("Generated queries do not match expected ones")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testEscapeCharacters() throws Exception {
        final File testFile = new File(getPath("InputXpathQueryGeneratorEscapeCharacters.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumberOne = 4;
        final int columnNumberOne = 22;
        final XpathQueryGenerator queryGeneratorOne = new XpathQueryGenerator(detailAst,
                lineNumberOne, columnNumberOne, testFileText, tabWidth);
        final List<String> actualTestOne = queryGeneratorOne.generate();
        final List<String> expectedTestOne = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGeneratorEscapeCharacters']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='testOne']]/ASSIGN/EXPR[./"
                        + "STRING_LITERAL[@text='&lt;&gt;&apos;&apos;\\&quot;&amp;abc;&amp;lt;"
                        + "\\u0080\\n']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGeneratorEscapeCharacters']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='testOne']]/ASSIGN/EXPR/"
                        + "STRING_LITERAL[@text='&lt;&gt;&apos;&apos;\\&quot;&amp;abc;&amp;lt;"
                        + "\\u0080\\n']"
        );
        assertWithMessage("Generated queries do not match expected ones")
            .that(actualTestOne)
            .isEqualTo(expectedTestOne);

        final int lineNumberTwo = 6;
        final int columnNumberTwo = 22;
        final XpathQueryGenerator queryGeneratorTwo = new XpathQueryGenerator(detailAst,
                lineNumberTwo, columnNumberTwo, testFileText, tabWidth);
        final List<String> actualTestTwo = queryGeneratorTwo.generate();
        final List<String> expectedTestTwo = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathQueryGeneratorEscapeCharacters']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='testTwo']]/ASSIGN/EXPR[./"
                        + "STRING_LITERAL[@text='&amp;#0;&amp;#X0\\u0001\\r']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGeneratorEscapeCharacters']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='testTwo']]/ASSIGN/EXPR/"
                        + "STRING_LITERAL[@text='&amp;#0;&amp;#X0\\u0001\\r']"
        );
        assertWithMessage("Generated queries do not match expected ones")
            .that(actualTestTwo)
            .isEqualTo(expectedTestTwo);
    }

    @Test
    public void testTextBlocks() throws Exception {
        final File testFile = new File(getNonCompilablePath(
                "InputXpathQueryGeneratorTextBlock.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumber = 6;
        final int columnNumber = 25;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst,
                lineNumber, columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathQueryGeneratorTextBlock']]/OBJBLOCK/"
                    + "VARIABLE_DEF[./IDENT[@text='testOne']]/ASSIGN/EXPR/"
                    + "TEXT_BLOCK_LITERAL_BEGIN/TEXT_BLOCK_CONTENT[@text='\\n        "
                    + "&amp;1line\\n        &gt;2line\\n        &lt;3line\\n        ']"
            );
        assertWithMessage("Generated queries do not match expected ones")
                .that(expected)
                .isEqualTo(actual);
    }

    @Test
    public void testTextBlocksWithNewLine() throws Exception {
        final File testFile = new File(getNonCompilablePath(
                "InputXpathQueryGeneratorTextBlockNewLine.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumber = 6;
        final int columnNumber = 25;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst,
                lineNumber, columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGeneratorTextBlockNewLine']]/OBJBLOCK/"
                        + "VARIABLE_DEF[./IDENT[@text='testOne']]/ASSIGN/EXPR/"
                        + "TEXT_BLOCK_LITERAL_BEGIN/TEXT_BLOCK_CONTENT[@text='\\n        "
                        + "&amp;1line\\n\\n        &gt;2line\\n        &lt;3line\\n        ']"
        );
        assertWithMessage("Generated queries do not match expected ones")
                .that(expected)
                .isEqualTo(actual);
    }

    @Test
    public void testTextBlocksWithNewCrlf() throws Exception {
        final File testFile = new File(getNonCompilablePath(
                "InputXpathQueryGeneratorTextBlockCrlf.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumber = 6;
        final int columnNumber = 25;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(detailAst,
                lineNumber, columnNumber, testFileText, tabWidth);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGeneratorTextBlockCrlf']]/OBJBLOCK/"
                        + "VARIABLE_DEF[./IDENT[@text='testOne']]/ASSIGN/EXPR/"
                        + "TEXT_BLOCK_LITERAL_BEGIN/TEXT_BLOCK_CONTENT[@text='\\r\\n        "
                        + "&amp;1line\\r\\n\\r\\n        &gt;2line\\r\\n        &lt;3line\\r\\n"
                        + "        ']"
        );
        assertWithMessage("Generated queries do not match expected ones")
                .that(expected)
                .isEqualTo(actual);
    }

    @Test
    public void testXpath() throws Exception {
        final File testFile =
                new File(getPath("InputXpathQueryGenerator2.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumberOne = 7;
        final int columnNumberOne = 12;
        final XpathQueryGenerator queryGeneratorOne = new XpathQueryGenerator(detailAst,
                lineNumberOne, columnNumberOne, testFileText, tabWidth);
        final List<String> actualTestOne = queryGeneratorOne.generate();
        final List<String> expectedTestOne = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathQueryGenerator2']]"
                        + "/OBJBLOCK/ENUM_DEF[./IDENT[@text='Foo3']]/OBJBLOCK/COMMA[2]"
        );
        assertWithMessage("Generated queries do not match expected ones")
            .that(actualTestOne)
            .isEqualTo(expectedTestOne);
    }

    @Test
    public void testXpath2() throws Exception {
        final File testFile =
                new File(getPath("InputXpathQueryGenerator3.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumber3 = 13;
        final int columnNumber3 = 21;
        final XpathQueryGenerator queryGenerator3 = new XpathQueryGenerator(detailAst,
                lineNumber3, columnNumber3, testFileText, tabWidth);
        final List<String> actualTest3 = queryGenerator3.generate();
        final List<String> expectedTest3 = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator3']]"
                        + "/OBJBLOCK/SEMI[1]"
        );
        assertWithMessage("Generated queries do not match expected ones")
                .that(actualTest3)
                .isEqualTo(expectedTest3);
    }

    @Test
    public void testXpath3() throws Exception {
        final File testFile =
                new File(getPath("InputXpathQueryGenerator4.java"));
        final FileText testFileText = new FileText(testFile,
                StandardCharsets.UTF_8.name());
        final DetailAST detailAst =
                JavaParser.parseFile(testFile, JavaParser.Options.WITHOUT_COMMENTS);
        final int tabWidth = 8;

        final int lineNumber2 = 10;
        final int columnNumber2 = 17;
        final XpathQueryGenerator queryGenerator2 = new XpathQueryGenerator(detailAst,
                lineNumber2, columnNumber2, testFileText, tabWidth);
        final List<String> actualTest = queryGenerator2.generate();
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator4']]"
                + "/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='methodFallThruCustomWords']]/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP[./SLIST/EXPR/POST_INC/IDENT[@text='i']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator4']]"
                + "/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='methodFallThruCustomWords']]/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT"
        );
        assertWithMessage("Generated queries do not match expected ones")
            .that(actualTest)
            .isEqualTo(expectedXpathQueries);
    }
}
