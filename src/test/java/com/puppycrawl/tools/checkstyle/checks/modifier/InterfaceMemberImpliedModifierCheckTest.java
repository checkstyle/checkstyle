///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InterfaceMemberImpliedModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/interfacememberimpliedmodifier";
    }

    @Test
    public void testMethodsOnInterfaceNoImpliedPublicAbstract() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "36:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:5: " + getCheckMessage(MSG_KEY, "public"),
            "38:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface.java"),
            expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final InterfaceMemberImpliedModifierCheck check = new InterfaceMemberImpliedModifierCheck();
        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
        };
        assertWithMessage("Required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testMethodsOnInterfaceNoImpliedAbstractAllowImpliedPublic() throws Exception {
        final String[] expected = {
            "36:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface2.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceNoImpliedPublicAllowImpliedAbstract() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "38:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface3.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceAllowImpliedPublicAbstract() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface4.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnClass.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceNestedNoImpliedPublicAbstract() throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "29:9: " + getCheckMessage(MSG_KEY, "public"),
            "34:9: " + getCheckMessage(MSG_KEY, "public"),
            "36:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:9: " + getCheckMessage(MSG_KEY, "public"),
            "38:9: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnInterfaceNested.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNestedNoImpliedPublicAbstract() throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "29:9: " + getCheckMessage(MSG_KEY, "public"),
            "34:9: " + getCheckMessage(MSG_KEY, "public"),
            "36:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:9: " + getCheckMessage(MSG_KEY, "public"),
            "38:9: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierMethodsOnClassNested.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicStaticFinal() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "final"),
            "22:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "final"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "final"),
            "30:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicStaticAllowImpliedFinal() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface2.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicFinalAllowImpliedStatic() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "final"),
            "24:5: " + getCheckMessage(MSG_KEY, "final"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "final"),
            "30:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface3.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedStaticFinalAllowImpliedPublic() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "final"),
            "22:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "final"),
            "28:5: " + getCheckMessage(MSG_KEY, "final"),
            "30:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface4.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceAllowImpliedPublicStaticFinal() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface5.java"),
            expected);
    }

    @Test
    public void testFieldsOnClassIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierFieldsOnClass.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedPublicStatic() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "static"),
            "35:5: " + getCheckMessage(MSG_KEY, "static"),
            "40:5: " + getCheckMessage(MSG_KEY, "public"),
            "45:5: " + getCheckMessage(MSG_KEY, "public"),
            "45:5: " + getCheckMessage(MSG_KEY, "static"),
            "53:5: " + getCheckMessage(MSG_KEY, "static"),
            "56:5: " + getCheckMessage(MSG_KEY, "public"),
            "59:5: " + getCheckMessage(MSG_KEY, "public"),
            "59:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnInterface.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedStaticAllowImpliedPublic() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "static"),
            "27:5: " + getCheckMessage(MSG_KEY, "static"),
            "35:5: " + getCheckMessage(MSG_KEY, "static"),
            "45:5: " + getCheckMessage(MSG_KEY, "static"),
            "53:5: " + getCheckMessage(MSG_KEY, "static"),
            "59:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnInterface2.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedPublicAllowImpliedStatic() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "40:5: " + getCheckMessage(MSG_KEY, "public"),
            "45:5: " + getCheckMessage(MSG_KEY, "public"),
            "56:5: " + getCheckMessage(MSG_KEY, "public"),
            "59:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnInterface3.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceAllowImpliedPublicStatic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnInterface4.java"),
            expected);
    }

    @Test
    public void testNestedOnClassIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnClass.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNestedNoImpliedPublicStatic() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY, "public"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "23:9: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnInterfaceNested.java"),
            expected);
    }

    @Test
    public void testNestedOnClassNestedNoImpliedPublicStatic() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY, "public"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "23:9: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierNestedOnClassNested.java"),
            expected);
    }

    @Test
    public void testPackageScopeInterface() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, "final"),
            "22:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "final"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "final"),
            "30:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
            "32:5: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "final"),
            "37:5: " + getCheckMessage(MSG_KEY, "public"),
            "43:5: " + getCheckMessage(MSG_KEY, "public"),
            "48:5: " + getCheckMessage(MSG_KEY, "public"),
            "50:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "52:5: " + getCheckMessage(MSG_KEY, "public"),
            "52:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "57:5: " + getCheckMessage(MSG_KEY, "static"),
            "60:5: " + getCheckMessage(MSG_KEY, "public"),
            "63:5: " + getCheckMessage(MSG_KEY, "public"),
            "63:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierPackageScopeInterface.java"),
            expected);
    }

    @Test
    public void testPrivateMethodsOnInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInterfaceMemberImpliedModifierPrivateMethods.java"),
            expected);
    }

    @Test
    public void testIllegalState() {
        final DetailAstImpl init = new DetailAstImpl();
        init.setType(TokenTypes.STATIC_INIT);
        final DetailAstImpl objBlock = new DetailAstImpl();
        objBlock.setType(TokenTypes.OBJBLOCK);
        objBlock.addChild(init);
        final DetailAstImpl interfaceAst = new DetailAstImpl();
        interfaceAst.setType(TokenTypes.INTERFACE_DEF);
        interfaceAst.addChild(objBlock);
        final InterfaceMemberImpliedModifierCheck check =
            new InterfaceMemberImpliedModifierCheck();
        try {
            check.visitToken(init);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(init.toString());
        }
    }

    @Test
    public void testSealedClassInInterface() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY, "final"),
            "18:5: " + getCheckMessage(MSG_KEY, "public"),
            "18:5: " + getCheckMessage(MSG_KEY, "static"),
            "23:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "static"),
            "31:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "31:9: " + getCheckMessage(MSG_KEY, "public"),
            "36:5: " + getCheckMessage(MSG_KEY, "static"),
            "36:5: " + getCheckMessage(MSG_KEY, "public"),
            "40:5: " + getCheckMessage(MSG_KEY, "public"),
            "40:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputInterfaceMemberImpliedModifierSealedClasses.java"),
            expected);
    }
}
