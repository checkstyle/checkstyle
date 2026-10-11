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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modifier.AnnotatedDeclarationVisibilityCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AnnotatedDeclarationVisibilityCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/annotateddeclarationvisibility";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotatedDeclarationVisibilityCheck annotatedMethodVisibilityModifierCheck =
                new AnnotatedDeclarationVisibilityCheck();
        final int[] actual = annotatedMethodVisibilityModifierCheck.getRequiredTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
        };
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testCloneInVisibilityProperty() {
        final AccessModifierOption[] input = {
            AccessModifierOption.PACKAGE,
        };
        final AnnotatedDeclarationVisibilityCheck check =
                new AnnotatedDeclarationVisibilityCheck();
        check.setVisibility(input);

        assertWithMessage("check creates its own instance of visibility array")
            .that(TestUtil.getInternalState(
                    check, "visibility", AccessModifierOption[].class))
            .isNotSameInstanceAs(input);
    }

    @Test
    public void testBasic() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "private"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "40:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotatedDeclarationVisibilityBasic.java"),
            expected);
    }

    @Test
    public void testSimpleImport() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "public"),
            "26:5: " + getCheckMessage(MSG_KEY, "private"),
            "33:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotatedDeclarationVisibilitySimpleImport.java"),
            expected);
    }

    @Test
    public void testStarImport() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "public"),
            "29:5: " + getCheckMessage(MSG_KEY, "private"),
            "36:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotatedDeclarationVisibilityStarImport.java"),
            expected);
    }

    @Test
    public void testCustomVisibility() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "protected"),
            "24:5: " + getCheckMessage(MSG_KEY, "private"),
            "28:5: " + getCheckMessage(MSG_KEY, "package-private"),
            "35:5: " + getCheckMessage(MSG_KEY, "protected"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityCustomVisibility.java"),
            expected);
    }

    @Test
    public void testMultipleConfiguredAnnotations() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "private"),
            "46:5: " + getCheckMessage(MSG_KEY, "public"),
            "51:5: " + getCheckMessage(MSG_KEY, "private"),
            "59:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityMultipleConfigured.java"),
            expected);
    }

    @Test
    public void testInterfaceImplicitVisibility() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "public"),
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "31:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityInterfaceImplicit.java"),
            expected);
    }

    @Test
    public void testLocalVariablesIgnored() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityLocalVariables.java"),
            expected);
    }

    @Test
    public void testAllDefinitionTypes() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "private"),
            "35:5: " + getCheckMessage(MSG_KEY, "public"),
            "43:5: " + getCheckMessage(MSG_KEY, "public"),
            "51:5: " + getCheckMessage(MSG_KEY, "public"),
            "59:5: " + getCheckMessage(MSG_KEY, "public"),
            "69:5: " + getCheckMessage(MSG_KEY, "public"),
            "79:5: " + getCheckMessage(MSG_KEY, "private"),
            "87:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityAllDefinitions.java"),
            expected);
    }

    @Test
    public void testSamePackageAnnotation() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "public"),
            "25:5: " + getCheckMessage(MSG_KEY, "private"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilitySamePackage.java"),
            expected);
    }

    @Test
    public void testNonDefaultTokens() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "protected"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityNonDefaultTokens.java"),
            expected);
    }

    @Test
    public void testImport() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "private"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityImport.java"),
            expected);
    }

    @Test
    public void testImportsClearedOnBeginTree() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "private"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
            getPath("InputAnnotatedDeclarationVisibilityClear1.java"),
            getPath("InputAnnotatedDeclarationVisibilityClear2.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getNonCompilablePath(
                    "compact/InputAnnotatedDeclarationVisibilityCompactSourceFile.java"),
            expected);
    }

    @Test
    public void testCompactSourceFileCustom() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY, "private"),
            "23:1: " + getCheckMessage(MSG_KEY, "public"),
            "27:1: " + getCheckMessage(MSG_KEY, "package-private"),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath(
                    "compact/InputAnnotatedDeclarationVisibilityCompactSourceFileCustom.java"),
            expected);
    }

}
