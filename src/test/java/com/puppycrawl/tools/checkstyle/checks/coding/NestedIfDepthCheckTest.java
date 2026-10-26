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
import static com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NestedIfDepthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nestedifdepth";
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_KEY, 2, 1),
            "15:17: " + getCheckMessage(MSG_KEY, 3, 1),
            "40:13: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedIfDepthCompactSourceFile.java"), expected);
    }

    @Test
    public void testCompactSourceFileMax() throws Exception {
        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputNestedIfDepthCompactSourceFileMax.java"),
                expected);
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "26:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "52:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "77:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "102:18: " + getCheckMessage(MSG_KEY, 2, 1),
            "107:18: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedIfDepthDefault.java"), expected);
    }

    @Test
    public void testCustomizedDepth() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputNestedIfDepthMax.java"), expected);
    }

    @Test
    public void testInsideCaseBody() throws Exception {

        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedIfDepthInsideCaseBody.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NestedIfDepthCheck check = new NestedIfDepthCheck();
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
    public void testClearState() throws Exception {
        final NestedIfDepthCheck check = new NestedIfDepthCheck();
        final Optional<DetailAST> literalIf = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(new File(getPath("InputNestedIfDepthDefault.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.LITERAL_IF);

        assertWithMessage("Ast should contain LITERAL_IF")
                .that(literalIf.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalIf.orElseThrow(), "depth",
                        depth -> ((Number) depth).intValue() == 0))
                .isTrue();
    }

}
