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

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
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
    public void testPrivatePredicatesCoverage() throws Exception {
        final Method isSemicolonInCompilationUnit = EmptyStatementCheck.class
            .getDeclaredMethod("isSemicolonInCompilationUnit", com.puppycrawl.tools.checkstyle.api.DetailAST.class);
        isSemicolonInCompilationUnit.setAccessible(true);
        final Method isStandaloneSemicolonInTypeBody = EmptyStatementCheck.class
            .getDeclaredMethod("isStandaloneSemicolonInTypeBody", com.puppycrawl.tools.checkstyle.api.DetailAST.class);
        isStandaloneSemicolonInTypeBody.setAccessible(true);
        final Method isSemicolonAfterTypeMember = EmptyStatementCheck.class
            .getDeclaredMethod("isSemicolonAfterTypeMember", com.puppycrawl.tools.checkstyle.api.DetailAST.class);
        isSemicolonAfterTypeMember.setAccessible(true);
        final Method isTypeMemberDeclaration = EmptyStatementCheck.class
            .getDeclaredMethod("isTypeMemberDeclaration", int.class);
        isTypeMemberDeclaration.setAccessible(true);

        final DetailAstImpl compilationUnit = ast(TokenTypes.COMPILATION_UNIT);
        final DetailAstImpl semicolonInCompilationUnit = ast(TokenTypes.SEMI);
        compilationUnit.addChild(semicolonInCompilationUnit);
        assertWithMessage("Expected semicolon directly under compilation unit")
            .that((boolean) isSemicolonInCompilationUnit.invoke(null, semicolonInCompilationUnit))
            .isTrue();

        final DetailAstImpl classDef = ast(TokenTypes.CLASS_DEF);
        final DetailAstImpl classObjBlock = ast(TokenTypes.OBJBLOCK);
        classDef.addChild(classObjBlock);
        final DetailAstImpl standaloneSemicolon = ast(TokenTypes.SEMI);
        classObjBlock.addChild(standaloneSemicolon);
        assertWithMessage("Expected standalone semicolon in class body")
            .that((boolean) isStandaloneSemicolonInTypeBody.invoke(null, standaloneSemicolon))
            .isTrue();

        final DetailAstImpl enumDef = ast(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjBlock = ast(TokenTypes.OBJBLOCK);
        enumDef.addChild(enumObjBlock);
        final DetailAstImpl enumSemicolon = ast(TokenTypes.SEMI);
        enumObjBlock.addChild(enumSemicolon);
        assertWithMessage("Expected enum-body semicolon to be ignored")
            .that((boolean) isStandaloneSemicolonInTypeBody.invoke(null, enumSemicolon))
            .isFalse();

        final DetailAstImpl memberObjBlock = ast(TokenTypes.OBJBLOCK);
        final DetailAstImpl methodDef = ast(TokenTypes.METHOD_DEF);
        memberObjBlock.addChild(methodDef);
        final DetailAstImpl memberSemicolon = ast(TokenTypes.SEMI);
        memberObjBlock.addChild(memberSemicolon);
        assertWithMessage("Expected semicolon after method declaration")
            .that((boolean) isSemicolonAfterTypeMember.invoke(null, memberSemicolon))
            .isTrue();
        assertWithMessage("Expected semicolon after method declaration to be ignored")
            .that((boolean) isStandaloneSemicolonInTypeBody.invoke(null, memberSemicolon))
            .isFalse();

        final DetailAstImpl slist = ast(TokenTypes.SLIST);
        final DetailAstImpl explicitEmptyStatement = ast(TokenTypes.EMPTY_STAT);
        slist.addChild(explicitEmptyStatement);
        assertWithMessage("Expected empty statement in code block to not be a member terminator")
            .that((boolean) isSemicolonAfterTypeMember.invoke(null, explicitEmptyStatement))
            .isFalse();

        assertWithMessage("Expected METHOD_DEF to be type member declaration")
            .that((boolean) isTypeMemberDeclaration.invoke(null, TokenTypes.METHOD_DEF))
            .isTrue();
        assertWithMessage("Expected SEMI to not be type member declaration")
            .that((boolean) isTypeMemberDeclaration.invoke(null, TokenTypes.SEMI))
            .isFalse();

        final EmptyStatementCheck check = new EmptyStatementCheck();
        final DetailAstImpl nonRequiredToken = ast(TokenTypes.IDENT);
        final DetailAstImpl methodDefParent = ast(TokenTypes.METHOD_DEF);
        methodDefParent.addChild(nonRequiredToken);
        check.visitToken(nonRequiredToken);

        final DetailAstImpl semicolonInMethodSignature = ast(TokenTypes.SEMI);
        methodDefParent.addChild(semicolonInMethodSignature);
        check.visitToken(semicolonInMethodSignature);

        final DetailAstImpl emptyStatInMethodSignature = ast(TokenTypes.EMPTY_STAT);
        methodDefParent.addChild(emptyStatInMethodSignature);
        assertWithMessage("Expected empty statement under method definition to be member terminator")
            .that((boolean) isSemicolonAfterTypeMember.invoke(null, emptyStatInMethodSignature))
            .isTrue();
        check.visitToken(emptyStatInMethodSignature);
    }

    private static DetailAstImpl ast(int tokenType) {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(tokenType);
        return ast;
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
