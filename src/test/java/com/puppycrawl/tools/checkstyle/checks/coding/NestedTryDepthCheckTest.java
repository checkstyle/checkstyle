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
import static com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class NestedTryDepthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nestedtrydepth";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "29:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "40:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "41:21: " + getCheckMessage(MSG_KEY, 3, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedTryDepth.java"), expected);
    }

    @Test
    public void testCustomizedDepth() throws Exception {

        final String[] expected = {
            "41:21: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedTryDepthMax.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_KEY, 2, 1),
            "33:13: " + getCheckMessage(MSG_KEY, 2, 1),
            "40:13: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedTryDepthCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileCustom() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, 3, 2),
            "39:17: " + getCheckMessage(MSG_KEY, 3, 2),
            "49:17: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedTryDepthCompactSourceFileCustom.java"),
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
        final NestedTryDepthCheck check = new NestedTryDepthCheck();
        final Optional<DetailAST> literalTry = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputNestedTryDepth.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.LITERAL_TRY);

        assertWithMessage("Ast should contain LITERAL_TRY")
                .that(literalTry.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalTry.orElseThrow(), "depth", depth -> (Integer) depth == 0))
                .isTrue();
    }

    @Test
    public void testTokensNotNull() {
        final NestedTryDepthCheck check = new NestedTryDepthCheck();
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
