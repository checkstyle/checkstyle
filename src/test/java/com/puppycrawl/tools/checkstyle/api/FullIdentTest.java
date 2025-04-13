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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck.MSG_ORDERING;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;

public class FullIdentTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/fullident/";
    }

    @Test
    public void testToString() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.LITERAL_NEW);
        ast.setColumnNo(14);
        ast.setLineNo(15);
        ast.setText("MyTest");

        final DetailAstImpl parent = new DetailAstImpl();
        parent.setType(TokenTypes.OBJBLOCK);
        parent.setColumnNo(4);
        parent.setLineNo(4);
        parent.setText("MyParent");
        parent.setFirstChild(ast);

        final FullIdent indent = FullIdent.createFullIdent(ast);
        assertWithMessage("Invalid full indent")
                .that(indent.toString())
                .isEqualTo("MyTest[15x14]");
        assertWithMessage("Invalid text")
                .that(indent.getText())
                .isEqualTo("MyTest");
        assertWithMessage("Invalid line")
                .that(indent.getLineNo())
                .isEqualTo(15);
        assertWithMessage("Invalid column")
                .that(indent.getColumnNo())
                .isEqualTo(14);
    }

    @Test
    public void testCreateFullIdentBelow() {
        final DetailAST ast = new DetailAstImpl();

        final FullIdent indent = FullIdent.createFullIdentBelow(ast);
        assertWithMessage("Invalid full indent")
                .that(indent.getText())
                .isEqualTo("");
    }

    @Test
    public void testGetDetailAst() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentTestArrayType.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST packageName = packageDefinitionNode.getFirstChild().getNextSibling();
        final FullIdent ident = FullIdent.createFullIdent(packageName);
        assertWithMessage("Invalid full indent")
                .that(ident.getDetailAst().toString())
                .isEqualTo("com[1x8]");
    }

    @Test
    public void testNonValidCoordinatesWithNegative() {
        final FullIdent fullIdent = prepareFullIdentWithCoordinates(14, 15);
        assertWithMessage("Invalid full indent")
                .that(fullIdent.toString())
                .isEqualTo("MyTest.MyTestik[15x14]");
    }

    @Test
    public void testNonValidCoordinatesWithZero() {
        final FullIdent fullIdent = prepareFullIdentWithCoordinates(0, 0);
        assertWithMessage("Invalid full indent")
                .that(fullIdent.toString())
                .isEqualTo("MyTest.MyTestik[15x14]");
    }

    @Test
    public void testWithArrayCreateFullIdentWithArrayDeclare() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentTestArrayType.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST arrayDeclarator = packageDefinitionNode.getNextSibling()
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .findFirstToken(TokenTypes.TYPE)
                .getFirstChild();
        final FullIdent ident = FullIdent.createFullIdent(arrayDeclarator);
        assertWithMessage("Invalid full indent")
                .that(ident.toString())
                .isEqualTo("int[][][5x12]");
    }

    @Test
    public void testFullIdentAnnotation() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentAnnotation.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST methodDef = packageDefinitionNode
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getLastChild()
                .findFirstToken(TokenTypes.METHOD_DEF);

        final DetailAST parameter = methodDef
                .findFirstToken(TokenTypes.PARAMETERS)
                .getFirstChild()
                .getFirstChild()
                .getNextSibling()
                .getFirstChild();

        final FullIdent ident = FullIdent.createFullIdent(parameter);
        assertWithMessage("Invalid full indent")
                .that(ident.toString())
                .isEqualTo("char[][7x29]");
    }

    @Test
    public void testFullIdentArrayInit() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentArrayInit.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST variableDef = packageDefinitionNode
                .getNextSibling()
                .getLastChild()
                .findFirstToken(TokenTypes.VARIABLE_DEF);

        final DetailAST literalInt = variableDef
                .findFirstToken(TokenTypes.ASSIGN)
                .getFirstChild()
                .getFirstChild()
                .getFirstChild();

        final FullIdent ident = FullIdent.createFullIdent(literalInt);
        assertWithMessage("Invalid full indent")
                .that(ident.toString())
                .isEqualTo("int[4x32]");
    }

    private static FullIdent prepareFullIdentWithCoordinates(int columnNo, int lineNo) {
        final DetailAstImpl parent = new DetailAstImpl();
        parent.setType(TokenTypes.TYPE);
        parent.setColumnNo(1);
        parent.setLineNo(1);
        parent.setText("Parent");

        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.DOT);
        ast.setColumnNo(1);
        ast.setLineNo(2);
        ast.setText("Root");

        final DetailAstImpl ast2 = new DetailAstImpl();
        ast2.setType(TokenTypes.LE);
        ast2.setColumnNo(columnNo);
        ast2.setLineNo(lineNo);
        ast2.setText("MyTestik");

        final DetailAstImpl ast1 = new DetailAstImpl();
        ast1.setType(TokenTypes.LITERAL_NEW);
        ast1.setColumnNo(14);
        ast1.setLineNo(15);
        ast1.setText("MyTest");

        parent.addChild(ast);
        ast.addChild(ast1);
        ast.addChild(ast2);

        return FullIdent.createFullIdent(ast);
    }

    @Test
    public void testReturnNoAnnotation() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentReturnNoAnnotation.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST annotationNode = packageDefinitionNode.getFirstChild();
        final FullIdent ident = FullIdent.createFullIdent(annotationNode);
        assertWithMessage("Full ident text should be empty.")
                .that(ident.getText())
                .isEmpty();
    }

    @Test
    public void testFullyQualifiedStringArray() throws Exception {
        final FileText testFileText = new FileText(
                new File(getPath("InputFullIdentFullyQualifiedStringArray.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST packageDefinitionNode =
                JavaParser.parse(new FileContents(testFileText)).getFirstChild();
        final DetailAST objectBlock = packageDefinitionNode.getNextSibling().getLastChild();
        final DetailAST mainMethodNode = objectBlock.findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST parameter = mainMethodNode
                .findFirstToken(TokenTypes.PARAMETERS).getFirstChild();
        final DetailAST parameterType = parameter.findFirstToken(TokenTypes.TYPE);
        final FullIdent ident = FullIdent.createFullIdent(parameterType.getFirstChild());

        assertWithMessage("Full ident should match expected.")
                .that(ident.getText())
                .isEqualTo(String[].class.getCanonicalName());
    }

    @Test
    public void testCreateFullIdentBelow2() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(ImportOrderCheck.class,
                    MSG_ORDERING, "java.util.HashMap"),
        };

        verifyWithInlineConfigParser(getPath("InputFullIdent.java"),
                expected);
    }

    @Test
    public void testLiteralNewCondition() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE, "j"),
        };

        verifyWithInlineConfigParser(getPath("InputFullIdentLiteralNewCondition.java"),
                expected);
    }
}
