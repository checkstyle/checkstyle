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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.UnnecessaryFullyQualifiedTypeCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnnecessaryFullyQualifiedTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/unnecessaryfullyqualifiedtype";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnnecessaryFullyQualifiedTypeCheck checkObj =
                new UnnecessaryFullyQualifiedTypeCheck();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.DOT,
            TokenTypes.IDENT,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, "java.util.Map"),
            "17:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "25:35: " + getCheckMessage(MSG_KEY, "java.util.HashMap"),
            "29:33: " + getCheckMessage(MSG_KEY, "java.io.IOException"),
            "31:18: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "33:33: " + getCheckMessage(MSG_KEY, "java.util.Set"),
            "35:57: " + getCheckMessage(MSG_KEY, "java.util.Collection"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedType.java"),
            expected);
    }

    @Test
    public void testClash() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeClash.java"),
            expected);
    }

    @Test
    public void testImportClash() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeImportClash.java"),
            expected);
    }

    @Test
    public void testSamePackage() throws Exception {
        final String[] expected = {
            "12:89: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "unnecessaryfullyqualifiedtype.Helper"),
            "19:22: " + getCheckMessage(MSG_KEY, "java.lang.Runnable"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeSamePackage.java"),
            expected);
    }

    @Test
    public void testCoverage() throws Exception {
        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "17:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeCoverage.java"),
            expected);
    }

    @Test
    public void testClearState() throws Exception {
        final UnnecessaryFullyQualifiedTypeCheck check =
                new UnnecessaryFullyQualifiedTypeCheck();

        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputUnnecessaryFullyQualifiedType.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> importAst = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.IMPORT);

        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        importAst.orElseThrow(),
                        "qualifiedReferences",
                        qualifiedReferences -> {
                            return ((Collection<?>) qualifiedReferences).isEmpty();
                        }))
                .isTrue();
    }

}
