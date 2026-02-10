///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck.MSG_KEY;


import java.io.File;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyStatementCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/emptystatement";
    }

    @Test
    public void testEmptyStatements() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_KEY),
            "17:4: " + getCheckMessage(MSG_KEY),
            "21:7: " + getCheckMessage(MSG_KEY),
            "28:7: " + getCheckMessage(MSG_KEY),
            "33:19: " + getCheckMessage(MSG_KEY),
            "37:10: " + getCheckMessage(MSG_KEY),
            "40:16: " + getCheckMessage(MSG_KEY),
            "44:10: " + getCheckMessage(MSG_KEY),
            "54:10: " + getCheckMessage(MSG_KEY),
            "60:13: " + getCheckMessage(MSG_KEY),
            "62:13: " + getCheckMessage(MSG_KEY),
            "65:19: " + getCheckMessage(MSG_KEY),
            "69:10: " + getCheckMessage(MSG_KEY),
            "72:9: " + getCheckMessage(MSG_KEY),
            "77:10: " + getCheckMessage(MSG_KEY),
            "83:10: " + getCheckMessage(MSG_KEY),
            "87:10: " + getCheckMessage(MSG_KEY),
            "91:10: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputEmptyStatement.java"), expected);
    }

    @Test
    public void testEnumSemicolonIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumSemicolon.java"), expected);
    }

    @Test
    public void testNonTypeSemicolonIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementNonTypeSemicolon.java"), expected);
    }

    @Test
    public void testCompilationUnitSemicolonIsEmptyStatement() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementBeforeClass.java"), expected);
    }

    @Test
    public void testSemicolonAfterTypeMemberIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementAfterTypeMember.java"), expected);
    }

    @Test
    public void testSemicolonAfterDefaultMethodIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementAfterDefaultMethod.java"), expected);
    }

    @Test
    public void testSemicolonAfterInnerTypeIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementAfterInnerType.java"), expected);
    }

    @Test
    public void testSemicolonInCompilationUnitVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl compilationUnit = ast(TokenTypes.COMPILATION_UNIT);
        final DetailAstImpl semicolon = ast(TokenTypes.SEMI);
        compilationUnit.addChild(semicolon);

        check.visitToken(semicolon);
    }

    @Test
    public void testSemicolonOutsideCompilationUnitVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl methodDef = ast(TokenTypes.METHOD_DEF);
        final DetailAstImpl semicolon = ast(TokenTypes.SEMI);
        methodDef.addChild(semicolon);

        check.visitToken(semicolon);
    }

    @Test
    public void testStandaloneSemicolonInTypeBodyVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl classDef = ast(TokenTypes.CLASS_DEF);
        final DetailAstImpl objBlock = ast(TokenTypes.OBJBLOCK);
        classDef.addChild(objBlock);
        final DetailAstImpl semicolon = ast(TokenTypes.SEMI);
        objBlock.addChild(semicolon);

        check.visitToken(semicolon);
    }

    @Test
    public void testEnumSemicolonInTypeBodyVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl enumDef = ast(TokenTypes.ENUM_DEF);
        final DetailAstImpl objBlock = ast(TokenTypes.OBJBLOCK);
        enumDef.addChild(objBlock);
        final DetailAstImpl semicolon = ast(TokenTypes.SEMI);
        objBlock.addChild(semicolon);

        check.visitToken(semicolon);
    }

    @Test
    public void testSemicolonAfterTypeMemberVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl objBlock = ast(TokenTypes.OBJBLOCK);
        final DetailAstImpl methodDef = ast(TokenTypes.METHOD_DEF);
        objBlock.addChild(methodDef);
        final DetailAstImpl semicolon = ast(TokenTypes.SEMI);
        objBlock.addChild(semicolon);

        check.visitToken(semicolon);
    }

    @Test
    public void testEmptyStatUnderMethodDefVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl methodDef = ast(TokenTypes.METHOD_DEF);
        final DetailAstImpl emptyStat = ast(TokenTypes.EMPTY_STAT);
        methodDef.addChild(emptyStat);

        check.visitToken(emptyStat);
    }

    @Test
    public void testEmptyStatUnderStatementListVisitToken() throws Exception {
        final EmptyStatementCheck check = newCheckWithFileContents();
        final DetailAstImpl slist = ast(TokenTypes.SLIST);
        final DetailAstImpl emptyStat = ast(TokenTypes.EMPTY_STAT);
        slist.addChild(emptyStat);

        check.visitToken(emptyStat);
    }

    private static DetailAstImpl ast(int tokenType) {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(tokenType);
        ast.setLineNo(1);
        ast.setColumnNo(0);
        return ast;
    }

    private static EmptyStatementCheck newCheckWithFileContents() throws Exception {
        final EmptyStatementCheck check = new EmptyStatementCheck();
        check.configure(createModuleConfig(EmptyStatementCheck.class));
        final FileText text = new FileText(new File("EmptyStatementCheckTest"),
            Collections.singletonList(";"));
        check.setFileContents(new FileContents(text));
        return check;
    }

    @Test
    public void testTokensNotNull() {
        final EmptyStatementCheck check = new EmptyStatementCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Default tokens should not be null")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Required tokens should not be null")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

}
