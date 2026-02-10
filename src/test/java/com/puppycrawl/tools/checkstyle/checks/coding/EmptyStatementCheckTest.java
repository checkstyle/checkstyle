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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
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

    @Test    public void testSemicolonInForLoopIsNotEmptyStatement() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
            getPath("InputEmptyStatementForLoopSemicolon.java"), expected);
    }

    @Test    public void testSemicolonInEnumWithFieldIsEmptyStatement() throws Exception {
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
        assertWithMessage("Semicolon with COMPILATION_UNIT parent should be treated as empty statement")
                .that(isAtCompUnitLevel)
                .isTrue();

        final DetailAstImpl semicolonInObjBlock = new DetailAstImpl();
        semicolonInObjBlock.setType(TokenTypes.SEMI);
        final DetailAstImpl objectBlock = new DetailAstImpl();
        objectBlock.setType(TokenTypes.OBJBLOCK);
        objectBlock.addChild(semicolonInObjBlock);

        final boolean isNotAtCompUnitLevel = TestUtil.invokeStaticMethod(
            EmptyStatementCheck.class,
            "isEmptyStatementAtCompilationUnitLevel",
            Boolean.class,
            semicolonInObjBlock);
        assertWithMessage("Semicolon with OBJBLOCK parent should not be at compilation unit level")
                .that(isNotAtCompUnitLevel)
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
        assertWithMessage("Semicolon without previous sibling should not be enum constants terminator")
                .that(isTerminatorWithoutSibling)
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
