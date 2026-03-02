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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
            "24:4: " + getCheckMessage(MSG_KEY),
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
    public void testEnumSimpleSemicolonIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumSimple.java"), expected);
    }

    @Test
    public void testExtraSemicolonInEnumIsEmptyStatement() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumExtraSemicolon.java"), expected);
    }

    @Test
    public void testSemicolonInEnumWithNoConstantsIsEmptyStatement() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumNoConstantsSemicolon.java"), expected);
    }

    @Test
    public void testSemicolonInEnumWithNoConstantsAndFieldIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumNoConstantsWithField.java"), expected);
    }

    @Test
    public void testSemicolonInClassBodyIsEmptyStatement() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementSemicolonInClassBody.java"), expected);
    }

    @Test
    public void testSemicolonInInterfaceBodyIsEmptyStatement() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementInterfaceBody.java"), expected);
    }

    @Test
    public void testSemicolonInForLoopIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementForLoopSemicolon.java"), expected);
    }

    @Test
    public void testSemicolonInEnumWithFieldIsEmptyStatement() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumWithField.java"), expected);
    }

    @Test
    public void testSemicolonInEnumWithMethodIsEmptyStatement() throws Exception {
        final String[] expected = {
            "14:6: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumWithMethod.java"), expected);
    }

    @Test
    public void testSemicolonInStatementIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementStatementWithSemiColon.java"), expected);
    }

    @Test
    public void testNonTypeSemicolonIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementNonTypeSemicolon.java"), expected);
    }

    @Test
    public void testStandaloneSemicolonBeforeType() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementStandaloneSemicolonBeforeType.java"), expected);
    }

    @Test
    public void testEnumSemicolonWithBodyIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumWithBody.java"), expected);
    }

    @Test
    public void testEnumTrailingCommaSemicolonIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementEnumTrailingCommaSemicolon.java"), expected);
    }

    @Test
    public void testEnumNoConstantsWithFieldSemicolonIsEnumTerminator() throws Exception {
        final DetailAST root = JavaParser.parseFile(
            new File(getPath("InputEmptyStatementEnumNoConstantsWithField.java")),
            JavaParser.Options.WITHOUT_COMMENTS);

        DetailAST semicolon = null;
        DetailAST ast = root;
        while (ast != null) {
            if (ast.getType() == TokenTypes.SEMI && ast.getLineNo() == 10) {
                semicolon = ast;
                break;
            }

            if (ast.getFirstChild() != null) {
                ast = ast.getFirstChild();
            }
            else {
                while (ast != null && ast.getNextSibling() == null) {
                    ast = ast.getParent();
                }
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
            }
        }

        assertWithMessage("Semicolon token on line 10 must exist")
                .that(semicolon)
                .isNotNull();

        final boolean isEnumTerminator = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolon);

        assertWithMessage("Semicolon in enum without constants but with members"
                + " should be enum constants terminator")
                .that(isEnumTerminator)
                .isTrue();
    }

    @Test
    public void testPrivateHelpersCoverage() throws Exception {
        final DetailAstImpl semicolonInCompUnit = new DetailAstImpl();
        semicolonInCompUnit.setType(TokenTypes.SEMI);
        final DetailAstImpl compilationUnit = new DetailAstImpl();
        compilationUnit.setType(TokenTypes.COMPILATION_UNIT);
        compilationUnit.addChild(semicolonInCompUnit);

        final boolean isAtCompUnitLevel = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEmptyStatementAtCompilationUnitLevel",
            Boolean.class,
            semicolonInCompUnit);
        assertWithMessage(
            "Semicolon with COMPILATION_UNIT parent should be treated as empty statement")
                .that(isAtCompUnitLevel)
                .isTrue();

        final DetailAstImpl semicolonInObjBlock = new DetailAstImpl();
        semicolonInObjBlock.setType(TokenTypes.SEMI);
        final DetailAstImpl objectBlock = new DetailAstImpl();
        objectBlock.setType(TokenTypes.OBJBLOCK);
        objectBlock.addChild(semicolonInObjBlock);

        final boolean isAtCompUnitLevelForObjBlock = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEmptyStatementAtCompilationUnitLevel",
            Boolean.class,
            semicolonInObjBlock);
        assertWithMessage("Semicolon with OBJBLOCK parent should not be at compilation unit level")
            .that(isAtCompUnitLevelForObjBlock)
                .isFalse();

        final DetailAstImpl enumBlockWithOnlySemicolon = new DetailAstImpl();
        enumBlockWithOnlySemicolon.setType(TokenTypes.OBJBLOCK);
        final DetailAstImpl semicolonWithoutSibling = new DetailAstImpl();
        semicolonWithoutSibling.setType(TokenTypes.SEMI);
        enumBlockWithOnlySemicolon.addChild(semicolonWithoutSibling);

        final boolean isTerminatorWithoutSibling = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonWithoutSibling);
        assertWithMessage(
            "Semicolon without previous sibling should not be enum constants terminator")
                .that(isTerminatorWithoutSibling)
                .isFalse();
    }

    @Test
    public void testEnumConstantsTerminatorGuards() throws Exception {
        final DetailAstImpl emptyStatWithoutSemicolon = createAst(TokenTypes.EMPTY_STAT);
        assertWithMessage("Empty statement without semicolon child is not enum terminator")
            .that(invokeIsEnumConstantsTerminator(emptyStatWithoutSemicolon))
            .isFalse();

        final DetailAstImpl semicolonWithoutParent = createAst(TokenTypes.SEMI);
        assertWithMessage("Semicolon without parent is not enum constants terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonWithoutParent))
            .isFalse();

        final DetailAstImpl semicolonWithCompUnitParent = createAst(TokenTypes.SEMI);
        final DetailAstImpl compilationUnitParent = createAst(TokenTypes.COMPILATION_UNIT);
        compilationUnitParent.addChild(semicolonWithCompUnitParent);
        assertWithMessage("Semicolon outside enum object block is not terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonWithCompUnitParent))
            .isFalse();

        final DetailAstImpl semicolonWithOrphanObjBlock = createAst(TokenTypes.SEMI);
        final DetailAstImpl orphanObjBlock = createAst(TokenTypes.OBJBLOCK);
        orphanObjBlock.addChild(semicolonWithOrphanObjBlock);
        assertWithMessage("Semicolon in orphan object block is not enum constants terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonWithOrphanObjBlock))
            .isFalse();

        final DetailAstImpl nonEnumSemicolon = createAst(TokenTypes.SEMI);
        final DetailAstImpl classObjectBlock = createAst(TokenTypes.OBJBLOCK);
        final DetailAstImpl classDef = createAst(TokenTypes.CLASS_DEF);
        classDef.addChild(classObjectBlock);
        classObjectBlock.addChild(nonEnumSemicolon);
        assertWithMessage("Semicolon in class body is not enum constants terminator")
            .that(invokeIsEnumConstantsTerminator(nonEnumSemicolon))
            .isFalse();
    }

    @Test
    public void testEnumConstantsTerminatorForConstantsList() throws Exception {
        final DetailAstImpl enumObjectBlockWithConstant = createEnumObjectBlock();
        final DetailAstImpl leftCurly = createAst(TokenTypes.LCURLY);
        final DetailAstImpl enumConstant = createAst(TokenTypes.ENUM_CONSTANT_DEF);
        final DetailAstImpl semicolonAfterConstant = createAst(TokenTypes.SEMI);
        enumObjectBlockWithConstant.addChild(leftCurly);
        enumObjectBlockWithConstant.addChild(enumConstant);
        enumObjectBlockWithConstant.addChild(semicolonAfterConstant);
        assertWithMessage("Semicolon after enum constant should be constants terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonAfterConstant))
            .isTrue();

        final DetailAstImpl enumObjectBlockWithComma = createEnumObjectBlock();
        final DetailAstImpl leftCurlyForComma = createAst(TokenTypes.LCURLY);
        final DetailAstImpl enumConstantForComma = createAst(TokenTypes.ENUM_CONSTANT_DEF);
        final DetailAstImpl comma = createAst(TokenTypes.COMMA);
        final DetailAstImpl semicolonAfterComma = createAst(TokenTypes.SEMI);
        enumObjectBlockWithComma.addChild(leftCurlyForComma);
        enumObjectBlockWithComma.addChild(enumConstantForComma);
        enumObjectBlockWithComma.addChild(comma);
        enumObjectBlockWithComma.addChild(semicolonAfterComma);
        assertWithMessage("Semicolon after comma in enum constants list should be terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonAfterComma))
            .isTrue();
    }

    @Test
    public void testEnumConstantsTerminatorForNoConstantsCases() throws Exception {
        final DetailAstImpl enumObjectBlockWithMembers = createEnumObjectBlock();
        final DetailAstImpl leftCurlyWithMembers = createAst(TokenTypes.LCURLY);
        final DetailAstImpl semicolonWithMembers = createAst(TokenTypes.SEMI);
        final DetailAstImpl fieldAfterSemicolon = createAst(TokenTypes.VARIABLE_DEF);
        enumObjectBlockWithMembers.addChild(leftCurlyWithMembers);
        enumObjectBlockWithMembers.addChild(semicolonWithMembers);
        enumObjectBlockWithMembers.addChild(fieldAfterSemicolon);
        assertWithMessage(
            "Semicolon in enum without constants but with members should be terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonWithMembers))
            .isTrue();

        final DetailAstImpl enumObjectBlockWithoutMembers = createEnumObjectBlock();
        final DetailAstImpl leftCurlyOnly = createAst(TokenTypes.LCURLY);
        final DetailAstImpl semicolonOnly = createAst(TokenTypes.SEMI);
        final DetailAstImpl rightCurlyOnly = createAst(TokenTypes.RCURLY);
        enumObjectBlockWithoutMembers.addChild(leftCurlyOnly);
        enumObjectBlockWithoutMembers.addChild(semicolonOnly);
        enumObjectBlockWithoutMembers.addChild(rightCurlyOnly);
        assertWithMessage("Standalone semicolon in empty enum should not be constants terminator")
            .that(invokeIsEnumConstantsTerminator(semicolonOnly))
            .isFalse();
    }

    @Test
    public void testEnumConstantsTerminatorEdgeShapes() throws Exception {
        final DetailAstImpl enumObjectBlockWithLeadingSemicolon = createEnumObjectBlock();
        final DetailAstImpl leadingSemicolon = createAst(TokenTypes.SEMI);
        final DetailAstImpl trailingRightCurly = createAst(TokenTypes.RCURLY);
        enumObjectBlockWithLeadingSemicolon.addChild(leadingSemicolon);
        enumObjectBlockWithLeadingSemicolon.addChild(trailingRightCurly);
        assertWithMessage("Semicolon with null previous sibling should not be enum terminator")
            .that(invokeIsEnumConstantsTerminator(leadingSemicolon))
            .isFalse();

        final DetailAstImpl enumObjectBlockWithTrailingSemicolonOnly = createEnumObjectBlock();
        final DetailAstImpl leftCurly = createAst(TokenTypes.LCURLY);
        final DetailAstImpl trailingSemicolonOnly = createAst(TokenTypes.SEMI);
        enumObjectBlockWithTrailingSemicolonOnly.addChild(leftCurly);
        enumObjectBlockWithTrailingSemicolonOnly.addChild(trailingSemicolonOnly);
        assertWithMessage("Semicolon without next sibling should not be enum terminator")
            .that(invokeIsEnumConstantsTerminator(trailingSemicolonOnly))
            .isFalse();

        final DetailAstImpl emptyStat = createAst(TokenTypes.EMPTY_STAT);
        final DetailAstImpl semicolonChild = createAst(TokenTypes.SEMI);
        emptyStat.addChild(semicolonChild);
        assertWithMessage("Synthetic EMPTY_STAT semicolon child is not enum constants terminator")
            .that(invokeIsEnumConstantsTerminator(emptyStat))
            .isFalse();
    }

    private static DetailAstImpl createAst(int tokenType) {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(tokenType);
        return ast;
    }

    private static DetailAstImpl createEnumObjectBlock() {
        final DetailAstImpl enumDef = createAst(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlock = createAst(TokenTypes.OBJBLOCK);
        enumDef.addChild(enumObjectBlock);
        return enumObjectBlock;
    }

    private static boolean invokeIsEnumConstantsTerminator(DetailAST ast) throws Exception {
        return TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            ast);
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
