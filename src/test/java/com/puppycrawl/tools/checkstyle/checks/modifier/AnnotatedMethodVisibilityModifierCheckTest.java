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
import static com.puppycrawl.tools.checkstyle.checks.modifier.AnnotatedMethodVisibilityModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotatedMethodVisibilityModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/annotatedmethodvisibilitymodifier";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotatedMethodVisibilityModifierCheck annotatedMethodVisibilityModifierCheck =
                new AnnotatedMethodVisibilityModifierCheck();
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
    public void testBasic() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "private"),
            "35:5: " + getCheckMessage(MSG_KEY, "public"),
            "41:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotatedMethodVisibilityModifierBasic.java"),
            expected);
    }

    @Test
    public void testSimpleImport() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "private"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotatedMethodVisibilityModifierSimpleImport.java"),
            expected);
    }

    @Test
    public void testStarImport() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "private"),
            "37:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotatedMethodVisibilityModifierStarImport.java"),
            expected);
    }

    @Test
    public void testCustomVisibility() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "protected"),
            "25:5: " + getCheckMessage(MSG_KEY, "private"),
            "29:10: " + getCheckMessage(MSG_KEY, "package-private"),
            "36:5: " + getCheckMessage(MSG_KEY, "protected"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierCustomVisibility.java"),
            expected);
    }

    @Test
    public void testMultipleConfiguredAnnotations() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "33:5: " + getCheckMessage(MSG_KEY, "private"),
            "48:5: " + getCheckMessage(MSG_KEY, "public"),
            "53:5: " + getCheckMessage(MSG_KEY, "private"),
            "61:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierMultipleConfigured.java"),
            expected);
    }

    @Test
    public void testInterfaceImplicitVisibility() throws Exception {
        final String[] expected = {
            "20:10: " + getCheckMessage(MSG_KEY, "public"),
            "24:10: " + getCheckMessage(MSG_KEY, "public"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "32:9: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierInterfaceImplicit.java"),
            expected);
    }

    @Test
    public void testLocalVariablesIgnored() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierLocalVariables.java"),
            expected);
    }

    @Test
    public void testAllDefinitionTypes() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "private"),
            "36:5: " + getCheckMessage(MSG_KEY, "public"),
            "44:5: " + getCheckMessage(MSG_KEY, "public"),
            "52:5: " + getCheckMessage(MSG_KEY, "public"),
            "60:5: " + getCheckMessage(MSG_KEY, "public"),
            "70:5: " + getCheckMessage(MSG_KEY, "public"),
            "80:5: " + getCheckMessage(MSG_KEY, "private"),
            "88:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierAllDefinitions.java"),
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
            getPath("InputAnnotatedMethodVisibilityModifierSamePackage.java"),
            expected);
    }

    @Test
    public void testNonDefaultTokens() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "protected"),
        };

        verifyWithInlineConfigParser(
            getPath("InputAnnotatedMethodVisibilityModifierNonDefaultTokens.java"),
            expected);
    }

    @Test
    public void testImport() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "public"),
            "29:5: " + getCheckMessage(MSG_KEY, "private"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotatedMethodVisibilityModifierImport.java"),
            expected);
    }

}
