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
    public void testEnumConstantsTerminatorBranchCoverage() throws Exception {
        final DetailAstImpl emptyStatWithoutSemicolon = new DetailAstImpl();
        emptyStatWithoutSemicolon.setType(TokenTypes.EMPTY_STAT);

        final boolean isTerminatorWhenSemicolonMissing = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            emptyStatWithoutSemicolon);
        assertWithMessage("Empty statement without semicolon child is not enum terminator")
                .that(isTerminatorWhenSemicolonMissing)
                .isFalse();

        final DetailAstImpl standaloneSemicolon = new DetailAstImpl();
        standaloneSemicolon.setType(TokenTypes.SEMI);

        final boolean isTerminatorWhenParentMissing = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            standaloneSemicolon);
        assertWithMessage("Semicolon without parent is not enum constants terminator")
                .that(isTerminatorWhenParentMissing)
                .isFalse();

        final DetailAstImpl semicolonWithCompUnitParent = new DetailAstImpl();
        semicolonWithCompUnitParent.setType(TokenTypes.SEMI);
        final DetailAstImpl compilationUnitParent = new DetailAstImpl();
        compilationUnitParent.setType(TokenTypes.COMPILATION_UNIT);
        compilationUnitParent.addChild(semicolonWithCompUnitParent);

        final boolean isTerminatorOutsideObjBlock = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonWithCompUnitParent);
        assertWithMessage("Semicolon outside enum object block is not terminator")
                .that(isTerminatorOutsideObjBlock)
                .isFalse();

        final DetailAstImpl semicolonWithOrphanObjBlock = new DetailAstImpl();
        semicolonWithOrphanObjBlock.setType(TokenTypes.SEMI);
        final DetailAstImpl orphanObjBlock = new DetailAstImpl();
        orphanObjBlock.setType(TokenTypes.OBJBLOCK);
        orphanObjBlock.addChild(semicolonWithOrphanObjBlock);

        final boolean isTerminatorWithMissingGrandParent = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonWithOrphanObjBlock);
        assertWithMessage("Semicolon in orphan object block is not enum constants terminator")
                .that(isTerminatorWithMissingGrandParent)
                .isFalse();

        final DetailAstImpl nonEnumSemicolon = new DetailAstImpl();
        nonEnumSemicolon.setType(TokenTypes.SEMI);
        final DetailAstImpl classObjectBlock = new DetailAstImpl();
        classObjectBlock.setType(TokenTypes.OBJBLOCK);
        final DetailAstImpl classDef = new DetailAstImpl();
        classDef.setType(TokenTypes.CLASS_DEF);
        classDef.addChild(classObjectBlock);
        classObjectBlock.addChild(nonEnumSemicolon);

        final boolean isTerminatorInNonEnum = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            nonEnumSemicolon);
        assertWithMessage("Semicolon in class body is not enum constants terminator")
                .that(isTerminatorInNonEnum)
                .isFalse();

        final DetailAstImpl enumDefWithoutSemicolon = new DetailAstImpl();
        enumDefWithoutSemicolon.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithoutSemicolon = new DetailAstImpl();
        enumObjectBlockWithoutSemicolon.setType(TokenTypes.OBJBLOCK);
        enumDefWithoutSemicolon.addChild(enumObjectBlockWithoutSemicolon);
        final DetailAstImpl enumMemberWithoutSemicolon = new DetailAstImpl();
        enumMemberWithoutSemicolon.setType(TokenTypes.VARIABLE_DEF);
        enumObjectBlockWithoutSemicolon.addChild(enumMemberWithoutSemicolon);

        final boolean isTerminatorWhenNoSemicolon = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            enumMemberWithoutSemicolon);
        assertWithMessage("Enum token should not be terminator when enum block has no semicolon")
                .that(isTerminatorWhenNoSemicolon)
                .isFalse();

        final DetailAstImpl enumDefWithTwoSemicolons = new DetailAstImpl();
        enumDefWithTwoSemicolons.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithTwoSemicolons = new DetailAstImpl();
        enumObjectBlockWithTwoSemicolons.setType(TokenTypes.OBJBLOCK);
        enumDefWithTwoSemicolons.addChild(enumObjectBlockWithTwoSemicolons);
        final DetailAstImpl firstSemicolon = new DetailAstImpl();
        firstSemicolon.setType(TokenTypes.SEMI);
        firstSemicolon.setLineNo(10);
        firstSemicolon.setColumnNo(5);
        final DetailAstImpl secondSemicolon = new DetailAstImpl();
        secondSemicolon.setType(TokenTypes.SEMI);
        secondSemicolon.setLineNo(11);
        secondSemicolon.setColumnNo(5);
        enumObjectBlockWithTwoSemicolons.addChild(firstSemicolon);
        enumObjectBlockWithTwoSemicolons.addChild(secondSemicolon);

        final boolean isSecondSemicolonTerminator = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            secondSemicolon);
        assertWithMessage("Only first semicolon in enum block may be constants terminator")
                .that(isSecondSemicolonTerminator)
                .isFalse();

        final DetailAstImpl enumDefWithConstant = new DetailAstImpl();
        enumDefWithConstant.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithConstant = new DetailAstImpl();
        enumObjectBlockWithConstant.setType(TokenTypes.OBJBLOCK);
        enumDefWithConstant.addChild(enumObjectBlockWithConstant);
        final DetailAstImpl leftCurly = new DetailAstImpl();
        leftCurly.setType(TokenTypes.LCURLY);
        final DetailAstImpl enumConstant = new DetailAstImpl();
        enumConstant.setType(TokenTypes.ENUM_CONSTANT_DEF);
        final DetailAstImpl semicolonAfterConstant = new DetailAstImpl();
        semicolonAfterConstant.setType(TokenTypes.SEMI);
        enumObjectBlockWithConstant.addChild(leftCurly);
        enumObjectBlockWithConstant.addChild(enumConstant);
        enumObjectBlockWithConstant.addChild(semicolonAfterConstant);

        final boolean isTerminatorAfterConstant = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonAfterConstant);
        assertWithMessage("Semicolon after enum constant should be constants terminator")
                .that(isTerminatorAfterConstant)
                .isTrue();

        final DetailAstImpl enumDefWithComma = new DetailAstImpl();
        enumDefWithComma.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithComma = new DetailAstImpl();
        enumObjectBlockWithComma.setType(TokenTypes.OBJBLOCK);
        enumDefWithComma.addChild(enumObjectBlockWithComma);
        final DetailAstImpl leftCurlyForComma = new DetailAstImpl();
        leftCurlyForComma.setType(TokenTypes.LCURLY);
        final DetailAstImpl enumConstantForComma = new DetailAstImpl();
        enumConstantForComma.setType(TokenTypes.ENUM_CONSTANT_DEF);
        final DetailAstImpl comma = new DetailAstImpl();
        comma.setType(TokenTypes.COMMA);
        final DetailAstImpl semicolonAfterComma = new DetailAstImpl();
        semicolonAfterComma.setType(TokenTypes.SEMI);
        enumObjectBlockWithComma.addChild(leftCurlyForComma);
        enumObjectBlockWithComma.addChild(enumConstantForComma);
        enumObjectBlockWithComma.addChild(comma);
        enumObjectBlockWithComma.addChild(semicolonAfterComma);

        final boolean isTerminatorAfterComma = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonAfterComma);
        assertWithMessage("Semicolon after comma in enum constants list should be terminator")
                .that(isTerminatorAfterComma)
                .isTrue();

        final DetailAstImpl enumDefWithoutConstantsWithMembers = new DetailAstImpl();
        enumDefWithoutConstantsWithMembers.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithoutConstantsWithMembers = new DetailAstImpl();
        enumObjectBlockWithoutConstantsWithMembers.setType(TokenTypes.OBJBLOCK);
        enumDefWithoutConstantsWithMembers.addChild(enumObjectBlockWithoutConstantsWithMembers);
        final DetailAstImpl leftCurlyWithoutConstants = new DetailAstImpl();
        leftCurlyWithoutConstants.setType(TokenTypes.LCURLY);
        final DetailAstImpl semicolonWithoutConstants = new DetailAstImpl();
        semicolonWithoutConstants.setType(TokenTypes.SEMI);
        final DetailAstImpl fieldAfterSemicolon = new DetailAstImpl();
        fieldAfterSemicolon.setType(TokenTypes.VARIABLE_DEF);
        enumObjectBlockWithoutConstantsWithMembers.addChild(leftCurlyWithoutConstants);
        enumObjectBlockWithoutConstantsWithMembers.addChild(semicolonWithoutConstants);
        enumObjectBlockWithoutConstantsWithMembers.addChild(fieldAfterSemicolon);

        final boolean isTerminatorWithoutConstantsWithMembers = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonWithoutConstants);
        assertWithMessage("Semicolon in enum without constants but with members should be terminator")
                .that(isTerminatorWithoutConstantsWithMembers)
                .isTrue();

        final DetailAstImpl enumDefWithoutConstantsAndMembers = new DetailAstImpl();
        enumDefWithoutConstantsAndMembers.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithoutConstantsAndMembers = new DetailAstImpl();
        enumObjectBlockWithoutConstantsAndMembers.setType(TokenTypes.OBJBLOCK);
        enumDefWithoutConstantsAndMembers.addChild(enumObjectBlockWithoutConstantsAndMembers);
        final DetailAstImpl leftCurlyOnly = new DetailAstImpl();
        leftCurlyOnly.setType(TokenTypes.LCURLY);
        final DetailAstImpl semicolonOnly = new DetailAstImpl();
        semicolonOnly.setType(TokenTypes.SEMI);
        final DetailAstImpl rightCurlyOnly = new DetailAstImpl();
        rightCurlyOnly.setType(TokenTypes.RCURLY);
        enumObjectBlockWithoutConstantsAndMembers.addChild(leftCurlyOnly);
        enumObjectBlockWithoutConstantsAndMembers.addChild(semicolonOnly);
        enumObjectBlockWithoutConstantsAndMembers.addChild(rightCurlyOnly);

        final boolean isTerminatorWithoutConstantsAndMembers = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            semicolonOnly);
        assertWithMessage("Standalone semicolon in empty enum should not be constants terminator")
                .that(isTerminatorWithoutConstantsAndMembers)
                .isFalse();

        final DetailAstImpl enumDefWithLeadingSemicolon = new DetailAstImpl();
        enumDefWithLeadingSemicolon.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithLeadingSemicolon = new DetailAstImpl();
        enumObjectBlockWithLeadingSemicolon.setType(TokenTypes.OBJBLOCK);
        enumDefWithLeadingSemicolon.addChild(enumObjectBlockWithLeadingSemicolon);
        final DetailAstImpl leadingSemicolon = new DetailAstImpl();
        leadingSemicolon.setType(TokenTypes.SEMI);
        final DetailAstImpl trailingRightCurly = new DetailAstImpl();
        trailingRightCurly.setType(TokenTypes.RCURLY);
        enumObjectBlockWithLeadingSemicolon.addChild(leadingSemicolon);
        enumObjectBlockWithLeadingSemicolon.addChild(trailingRightCurly);

        final boolean isLeadingSemicolonTerminator = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            leadingSemicolon);
        assertWithMessage("Semicolon with null previous sibling should not be enum terminator")
                .that(isLeadingSemicolonTerminator)
                .isFalse();

        final DetailAstImpl enumDefWithTrailingSemicolonOnly = new DetailAstImpl();
        enumDefWithTrailingSemicolonOnly.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithTrailingSemicolonOnly = new DetailAstImpl();
        enumObjectBlockWithTrailingSemicolonOnly.setType(TokenTypes.OBJBLOCK);
        enumDefWithTrailingSemicolonOnly.addChild(enumObjectBlockWithTrailingSemicolonOnly);
        final DetailAstImpl leftCurlyForTrailingSemicolonOnly = new DetailAstImpl();
        leftCurlyForTrailingSemicolonOnly.setType(TokenTypes.LCURLY);
        final DetailAstImpl trailingSemicolonOnly = new DetailAstImpl();
        trailingSemicolonOnly.setType(TokenTypes.SEMI);
        enumObjectBlockWithTrailingSemicolonOnly.addChild(leftCurlyForTrailingSemicolonOnly);
        enumObjectBlockWithTrailingSemicolonOnly.addChild(trailingSemicolonOnly);

        final boolean isTrailingSemicolonOnlyTerminator = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            trailingSemicolonOnly);
        assertWithMessage("Semicolon without next sibling should not be enum terminator")
                .that(isTrailingSemicolonOnlyTerminator)
                .isFalse();

        final DetailAstImpl enumDefWithEmptyStat = new DetailAstImpl();
        enumDefWithEmptyStat.setType(TokenTypes.ENUM_DEF);
        final DetailAstImpl enumObjectBlockWithEmptyStat = new DetailAstImpl();
        enumObjectBlockWithEmptyStat.setType(TokenTypes.OBJBLOCK);
        enumDefWithEmptyStat.addChild(enumObjectBlockWithEmptyStat);
        final DetailAstImpl leftCurlyForEmptyStat = new DetailAstImpl();
        leftCurlyForEmptyStat.setType(TokenTypes.LCURLY);
        final DetailAstImpl emptyStat = new DetailAstImpl();
        emptyStat.setType(TokenTypes.EMPTY_STAT);
        final DetailAstImpl semicolonChild = new DetailAstImpl();
        semicolonChild.setType(TokenTypes.SEMI);
        emptyStat.addChild(semicolonChild);
        final DetailAstImpl fieldAfterEmptyStat = new DetailAstImpl();
        fieldAfterEmptyStat.setType(TokenTypes.VARIABLE_DEF);
        enumObjectBlockWithEmptyStat.addChild(leftCurlyForEmptyStat);
        enumObjectBlockWithEmptyStat.addChild(emptyStat);
        enumObjectBlockWithEmptyStat.addChild(fieldAfterEmptyStat);

        final boolean isEmptyStatTerminator = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEnumConstantsTerminator",
            Boolean.class,
            emptyStat);
        assertWithMessage("Synthetic EMPTY_STAT semicolon child is not enum constants terminator")
                .that(isEmptyStatTerminator)
            .isFalse();
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
