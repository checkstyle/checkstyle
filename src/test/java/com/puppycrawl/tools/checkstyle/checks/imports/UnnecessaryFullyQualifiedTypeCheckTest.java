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
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

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
    public void testRedundantImport() throws Exception {
        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, "java.util.Map"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeRedundantImport.java"),
            expected);
    }

    @Test
    public void testSamePackage() throws Exception {
        final String[] expected = {
            "12:89: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "unnecessaryfullyqualifiedtype.Helper"),
            "17:22: " + getCheckMessage(MSG_KEY, "java.lang.Runnable"),
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
    public void testSamePackageHelper() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryFullyQualifiedTypeSamePackageHelper.java"),
            expected);
    }

    @Test
    public void testClearState() throws Exception {
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputUnnecessaryFullyQualifiedType.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        assertClearedOnBeginTree(root, TokenTypes.PACKAGE_DEF, "packageName",
                Objects::isNull);
        assertClearedOnBeginTree(root, TokenTypes.IMPORT, "importedTypes",
            importedTypes -> ((Map<?, ?>) importedTypes).isEmpty());
        assertClearedOnBeginTree(dotInTypeContext(root), "qualifiedReferences",
            qualifiedReferences -> ((Collection<?>) qualifiedReferences).isEmpty());
        assertClearedOnBeginTree(identWithParent(root, TokenTypes.CLASS_DEF), "declaredTypes",
            declaredTypes -> ((Collection<?>) declaredTypes).isEmpty());
        assertClearedOnBeginTree(identWithParent(root, TokenTypes.TYPE), "simpleReferences",
            simpleReferences -> ((Collection<?>) simpleReferences).isEmpty());
    }

    /**
     * Asserts that visiting the first node of the given type and then beginning a new
     * tree clears the given stateful field.
     *
     * @param root the root of the parsed input
     * @param tokenType the type of node to visit
     * @param fieldName the stateful field that should be cleared
     * @param isClear predicate that returns {@code true} when the field is cleared
     */
    private static void assertClearedOnBeginTree(DetailAST root, int tokenType,
            String fieldName, Predicate<Object> isClear) {
        final DetailAST nodeToVisit = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == tokenType).orElseThrow();
        assertClearedOnBeginTree(nodeToVisit, fieldName, isClear);
    }

    /**
     * Asserts that visiting the given node and then beginning a new tree clears the
     * given stateful field.
     *
     * @param nodeToVisit the node to visit
     * @param fieldName the stateful field that should be cleared
     * @param isClear predicate that returns {@code true} when the field is cleared
     */
    private static void assertClearedOnBeginTree(DetailAST nodeToVisit, String fieldName,
            Predicate<Object> isClear) {
        assertWithMessage("State '%s' is not cleared on beginTree", fieldName)
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(
                        new UnnecessaryFullyQualifiedTypeCheck(), nodeToVisit, fieldName, isClear))
                .isTrue();
    }

    /**
     * Finds the first fully qualified type reference node in type context.
     *
     * @param root the root of the parsed input
     * @return the {@link TokenTypes#DOT} node
     */
    private static DetailAST dotInTypeContext(DetailAST root) {
        return TestUtil.findTokenInAstByPredicate(root, ast -> {
            return ast.getType() == TokenTypes.DOT
                && ast.getParent().getType() == TokenTypes.TYPE;
        }).orElseThrow();
    }

    /**
     * Finds the first identifier whose parent is of the given type.
     *
     * @param root the root of the parsed input
     * @param parentType the type of the identifier's parent
     * @return the {@link TokenTypes#IDENT} node
     */
    private static DetailAST identWithParent(DetailAST root, int parentType) {
        return TestUtil.findTokenInAstByPredicate(root, ast -> {
            return ast.getType() == TokenTypes.IDENT
                && ast.getParent().getType() == parentType;
        }).orElseThrow();
    }

}
