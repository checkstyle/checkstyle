////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class XpathQueryGeneratorTest extends AbstractPathTestSupport {

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
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS/LITERAL_PUBLIC");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testMethodDef() {
        final int lineNumber = 45;
        final int columnNumber = 5;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/MODIFIERS/LITERAL_PUBLIC");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testVariableDef() {
        final int lineNumber = 53;
        final int columnNumber = 13;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/TYPE",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/LITERAL_FOR/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='d']]/TYPE/LITERAL_SHORT");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testLcurly() {
        final int lineNumber = 37;
        final int columnNumber = 20;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/LCURLY");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testRcurly() {
        final int lineNumber = 25;
        final int columnNumber = 5;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/INSTANCE_INIT/SLIST/RCURLY");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testExpr() {
        final int lineNumber = 17;
        final int columnNumber = 50;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/VARIABLE_DEF["
                + "./IDENT[@text='mUse4']]/ASSIGN/EXPR",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/VARIABLE_DEF["
                + "./IDENT[@text='mUse4']]/ASSIGN/EXPR/DOT");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testLparen() {
        final int lineNumber = 45;
        final int columnNumber = 31;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='callSomeMethod']]/LPAREN");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testEmpty() {
        final int lineNumber = 300;
        final int columnNumber = 300;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        assertTrue(actual.isEmpty(), "Result should be empty");
    }

    @Test
    public void testPackage() {
        final int lineNumber = 2;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/PACKAGE_DEF");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testImport() {
        final int lineNumber = 5;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/IMPORT[./DOT/IDENT[@text='File']]");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testMethodParams() {
        final int lineNumber = 72;
        final int columnNumber = 30;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/TYPE[./IDENT[@text='String']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='saveUser']]/PARAMETERS/PARAMETER_DEF[./IDENT[@text='name']]"
                + "/TYPE/IDENT[@text='String']");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testSwitch() {
        final int lineNumber = 37;
        final int columnNumber = 9;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testSwitchCase() {
        final int lineNumber = 38;
        final int columnNumber = 13;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/CASE_GROUP[1]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='Label']]/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testVariableStringLiteral() {
        final int lineNumber = 47;
        final int columnNumber = 26;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='HelloWorld']]",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='callSomeMethod']]/SLIST/VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='HelloWorld']");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testComma() {
        final int lineNumber = 66;
        final int columnNumber = 36;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='foo']]/SLIST/LITERAL_FOR/FOR_ITERATOR/ELIST/COMMA");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testLiteralVoid() {
        final int lineNumber = 65;
        final int columnNumber = 12;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='foo']]/TYPE",
            "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='foo']]/TYPE/LITERAL_VOID");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testFirstImport() {
        final int lineNumber = 4;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/IMPORT[./DOT/IDENT[@text='JToolBar']]");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testLastImport() {
        final int lineNumber = 8;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/IMPORT[./DOT/IDENT[@text='Iterator']]");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testImportByText() {
        final int lineNumber = 4;
        final int columnNumber = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/IMPORT/DOT[./IDENT[@text='JToolBar']]/DOT/IDENT[@text='javax']");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testIdent() {
        final int lineNumber = 12;
        final int columnNumber = 14;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF/IDENT[@text='InputXpathQueryGenerator']");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testTabWidthBeforeMethodDef() throws Exception {
        final File testFile = new File(getPath("InputXpathQueryGeneratorTabWidth.java"));
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
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]",
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS/LITERAL_PUBLIC");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testTabWidthAfterVoidLiteral() throws Exception {
        final File testFile = new File(getPath("InputXpathQueryGeneratorTabWidth.java"));
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
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/TYPE",
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/TYPE/LITERAL_VOID");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
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
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='tabAfterMe']]/SLIST");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
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
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGeneratorTabWidth']]/OBJBLOCK"
                        + "/VARIABLE_DEF[./IDENT[@text='endLineTab']]/SEMI");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testClassDefWithTokenType() {
        final int lineNumber = 12;
        final int columnNumber = 1;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber, TokenTypes.CLASS_DEF, fileText, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

    @Test
    public void testConstructorWithTreeWalkerAuditEvent() {
        final LocalizedMessage message = new LocalizedMessage(12, 1, "messages.properties", null,
                null, null, null, null, null);
        final TreeWalkerAuditEvent event = new TreeWalkerAuditEvent(new FileContents(fileText),
                "InputXpathQueryGenerator", message, rootAst);
        final XpathQueryGenerator queryGenerator =
                new XpathQueryGenerator(event, DEFAULT_TAB_WIDTH);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]",
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='InputXpathQueryGenerator']]/MODIFIERS/LITERAL_PUBLIC");
        assertEquals(expected, actual, "Generated queries do not match expected ones");
    }

}
