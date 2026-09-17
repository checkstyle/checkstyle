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

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
            "15:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "18:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
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
    public void testDirectImportShadowsOnDemandImport() throws Exception {
        final String[] expected = {
            "15:22: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryFullyQualifiedTypeDirectImportShadowsOnDemand.java"),
                expected);
    }

    @Test
    public void testTypeUseAnnotationEmbeddedInQualifiedName() throws Exception {
        final String[] expected = {
            "19:22: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryFullyQualifiedTypeAnnotated.java"),
                expected);
    }

    @Test
    public void testStateClearedBetweenFiles() throws Exception {
        final String fileName1 =
                getPath("InputUnnecessaryFullyQualifiedTypeClearStateFirst.java");
        final String fileName2 =
                getPath("InputUnnecessaryFullyQualifiedTypeClearStateTwo.java");
        final List<String> expectedFirst = Collections.emptyList();
        final List<String> expectedSecond = List.of(
                "12:22: " + getCheckMessage(MSG_KEY, "java.util.List")
        );
        verifyWithInlineConfigParser(fileName1, fileName2, expectedFirst, expectedSecond);
    }

    @Test
    public void testDeclaredTypesStateClearedBetweenFiles() throws Exception {
        final String fileName1 =
                getPath("InputUnnecessaryFullyQualifiedTypeDeclaredStateFirst.java");
        final String fileName2 =
                getPath("InputUnnecessaryFullyQualifiedTypeDeclaredStateTwo.java");
        final List<String> expectedFirst = Collections.emptyList();
        final List<String> expectedSecond = List.of(
                "12:22: " + getCheckMessage(MSG_KEY, "java.util.Timer"));
        verifyWithInlineConfigParser(fileName1, fileName2, expectedFirst, expectedSecond);

    }

    @Test
    public void testQualifiedReferencesStateClearedBetweenFiles() throws Exception {
        final String fileName1 =
                getPath("InputUnnecessaryFullyQualifiedTypeQualifiedStateFirst.java");
        final String fileName2 =
                getPath("InputUnnecessaryFullyQualifiedTypeQualifiedStateTwo.java");
        final List<String> expectedFirst = Collections.emptyList();
        final List<String> expectedSecond = List.of(
                "11:22: " + getCheckMessage(MSG_KEY, "java.util.Date"));
        verifyWithInlineConfigParser(fileName1, fileName2, expectedFirst, expectedSecond);
    }

    @Test
    public void testOnDemandImportPackagesStateClearedBetweenFiles() throws Exception {
        final String fileName1 =
                getPath("InputUnnecessaryFullyQualifiedTypeOnDemandClearStateFirst.java");
        final String fileName2 =
                getPath("InputUnnecessaryFullyQualifiedTypeOnDemandClearStateTwo.java");
        final List<String> expectedFirst = Collections.emptyList();
        final List<String> expectedSecond = List.of(
                "12:22: " + getCheckMessage(MSG_KEY, "java.util.List"));
        verifyWithInlineConfigParser(fileName1, fileName2, expectedFirst, expectedSecond);
    }

    @Test
    public void testClearStatePendingFields() {
        final UnnecessaryFullyQualifiedTypeCheck check =
                new UnnecessaryFullyQualifiedTypeCheck();

        TestUtil.setInternalState(check, "packageName", "some.stale.package");
        check.beginTree(null);
        final String packageName =
                TestUtil.getInternalState(check, "packageName", String.class);
        assertWithMessage("packageName field should be cleared on beginTree")
                .that(packageName)
                .isNull();
    }

}
