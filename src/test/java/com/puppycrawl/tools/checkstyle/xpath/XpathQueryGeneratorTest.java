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

package com.puppycrawl.tools.checkstyle.xpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;

public class XpathQueryGeneratorTest extends AbstractPathTestSupport {

    private static DetailAST rootAst;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathquerygenerator";
    }

    @Before
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathQueryGenerator.java"));
        rootAst = TestUtils.parseFile(file);
    }

    @Test
    public void testClassDef() {
        final int lineNumber = 12;
        final int columnNumber = 0;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/MODIFIERS",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/MODIFIERS/LITERAL_PUBLIC");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testMethodDef() {
        final int lineNumber = 45;
        final int columnNumber = 4;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']"
                    + "/OBJBLOCK/METHOD_DEF[@text='callSomeMethod']",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']"
                    + "/OBJBLOCK/METHOD_DEF[@text='callSomeMethod']/MODIFIERS",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']"
                    + "/OBJBLOCK/METHOD_DEF[@text='callSomeMethod']/MODIFIERS/LITERAL_PUBLIC");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testVariableDef() {
        final int lineNumber = 53;
        final int columnNumber = 12;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/LITERAL_FOR"
                    + "/SLIST/VARIABLE_DEF[@text='d']",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/LITERAL_FOR"
                    + "/SLIST/VARIABLE_DEF[@text='d']/MODIFIERS",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/LITERAL_FOR"
                    + "/SLIST/VARIABLE_DEF[@text='d']/TYPE",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/LITERAL_FOR"
                    + "/SLIST/VARIABLE_DEF[@text='d']/TYPE/LITERAL_SHORT");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testLcurly() {
        final int lineNumber = 37;
        final int columnNumber = 19;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK/METHOD_DEF[@text='Label']"
                    + "/SLIST/LITERAL_SWITCH/LCURLY");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testRcurly() {
        final int lineNumber = 25;
        final int columnNumber = 4;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK/INSTANCE_INIT"
                    + "/SLIST/RCURLY");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testExpr() {
        final int lineNumber = 17;
        final int columnNumber = 49;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='mUse4']/ASSIGN/EXPR",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='mUse4']/ASSIGN/EXPR/DOT");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testLparen() {
        final int lineNumber = 45;
        final int columnNumber = 30;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/LPAREN");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testEmpty() {
        final int lineNumber = 300;
        final int columnNumber = 300;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        assertTrue("Result should be empty", actual.isEmpty());
    }

    @Test
    public void testPackage() {
        final int lineNumber = 2;
        final int columnNumber = 0;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/PACKAGE_DEF");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testImport() {
        final int lineNumber = 5;
        final int columnNumber = 0;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/IMPORT[./DOT[@text='File']]");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testMethodParams() {
        final int lineNumber = 72;
        final int columnNumber = 29;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='saveUser']/PARAMETERS",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='saveUser']/PARAMETERS/PARAMETER_DEF[@text='name']",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='saveUser']/PARAMETERS/PARAMETER_DEF[@text='name']"
                    + "/MODIFIERS",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='saveUser']/PARAMETERS/PARAMETER_DEF[@text='name']"
                    + "/TYPE[@text='String']");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testSwitch() {
        final int lineNumber = 37;
        final int columnNumber = 8;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='Label']/SLIST/LITERAL_SWITCH");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testSwitchCase() {
        final int lineNumber = 38;
        final int columnNumber = 12;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK/METHOD_DEF[@text='Label']"
                    + "/SLIST/LITERAL_SWITCH/CASE_GROUP",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK/METHOD_DEF[@text='Label']"
                    + "/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testVariableStringLiteral() {
        final int lineNumber = 47;
        final int columnNumber = 25;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/VARIABLE_DEF[@text='another']"
                    + "/ASSIGN/EXPR",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='callSomeMethod']/SLIST/VARIABLE_DEF[@text='another']"
                    + "/ASSIGN/EXPR/STRING_LITERAL");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testComma() {
        final int lineNumber = 66;
        final int columnNumber = 35;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK/METHOD_DEF[@text='foo']"
                    + "/SLIST/LITERAL_FOR/FOR_ITERATOR/ELIST/COMMA");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testLiteralVoid() {
        final int lineNumber = 65;
        final int columnNumber = 11;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Arrays.asList(
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='foo']/TYPE",
            "/CLASS_DEF[@text='InputXpathQueryGenerator']/OBJBLOCK"
                    + "/METHOD_DEF[@text='foo']/TYPE/LITERAL_VOID");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testFirstImport() {
        final int lineNumber = 4;
        final int columnNumber = 0;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/IMPORT[./DOT[@text='JToolBar']]");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }

    @Test
    public void testLastImport() {
        final int lineNumber = 8;
        final int columnNumber = 0;
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst, lineNumber,
                columnNumber);
        final List<String> actual = queryGenerator.generate();
        final List<String> expected = Collections.singletonList(
                "/IMPORT[./DOT[@text='Iterator']]");
        assertEquals("Generated queries do not match expected ones", expected, actual);
    }
}
