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
import static com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NestedForDepthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nestedfordepth";
    }

    /**
     * Call the check allowing 2 layers of nested for-statements. This
     * means the top-level for can contain up to 2 levels of nested for
     * statements. As the test input has 4 layers of for-statements below
     * the top-level for statement, this must cause 2 error-messages.
     *
     * @throws Exception necessary to fulfill JUnit's interface-requirements for test-methods.
     */
    @Test
    public void testNestedForDepthCheckCustomMaxLevelTwo() throws Exception {

        final String[] expected = {
            "32:11: " + getCheckMessage(MSG_KEY, 3, 2),
            "33:13: " + getCheckMessage(MSG_KEY, 4, 2),
            "36:13: " + getCheckMessage(MSG_KEY, 4, 2),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedForDepthCheckCustomMaxLevelTwo.java"),
               expected);
    }

    /**
     * Call the check allowing 4 layers of nested for-statements. This
     * means the top-level for can contain up to 4 levels of nested for
     * statements. As the test input has 4 layers of for-statements below
     * the top-level for statement, this must not cause an error-message.
     *
     * @throws Exception necessary to fulfill JUnit's interface-requirements for test-methods.
     */
    @Test
    public void testNestedForDepthCheckCustomMaxLevelFour() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputNestedForDepthCheckCustomMaxLevelFour.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final NestedForDepthCheck check = new NestedForDepthCheck();
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

    @Test
    public void testNestedForDepthCheckDefaultMaxLevel() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedForDepthCheckDefaultMaxLevel.java"),
               expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, 2, 1),
            "20:17: " + getCheckMessage(MSG_KEY, 3, 1),
            "33:13: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedForDepthCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileCustom() throws Exception {
        final String[] expected = {
            "21:17: " + getCheckMessage(MSG_KEY, 3, 2),
            "35:17: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedForDepthCompactSourceFileCustom.java"),
                expected);
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testClearState() throws Exception {
        final NestedForDepthCheck check = new NestedForDepthCheck();
        final Optional<DetailAST> literalFor = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputNestedForDepthCheckDefaultMaxLevel.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.LITERAL_FOR);

        assertWithMessage("Ast should contain LITERAL_FOR")
                .that(literalFor.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalFor.orElseThrow(), "depth", depth -> (Integer) depth == 0))
                .isTrue();
    }

}
