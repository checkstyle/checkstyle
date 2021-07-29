////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "36:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:5: " + getCheckMessage(MSG_KEY, "public"),
            "38:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface.java"),
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
        assertArrayEquals(expected, actual, "Required tokens are invalid");
    }

    @Test
    public void testMethodsOnInterfaceNoImpliedAbstractAllowImpliedPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicMethod", "false");
        final String[] expected = {
            "36:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface2.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceNoImpliedPublicAllowImpliedAbstract() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedAbstractMethod", "false");
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "38:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface3.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceAllowImpliedPublicAbstract() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicMethod", "false");
        checkConfig.addProperty("violateImpliedAbstractMethod", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnInterface4.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnClass.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterfaceNestedNoImpliedPublicAbstract() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "29:9: " + getCheckMessage(MSG_KEY, "public"),
            "34:9: " + getCheckMessage(MSG_KEY, "public"),
            "36:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:9: " + getCheckMessage(MSG_KEY, "public"),
            "38:9: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(
            checkConfig,
            getPath("InputInterfaceMemberImpliedModifierMethodsOnInterfaceNested.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNestedNoImpliedPublicAbstract() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "29:9: " + getCheckMessage(MSG_KEY, "public"),
            "34:9: " + getCheckMessage(MSG_KEY, "public"),
            "36:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "38:9: " + getCheckMessage(MSG_KEY, "public"),
            "38:9: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierMethodsOnClassNested.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicStaticFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
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
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicStaticAllowImpliedFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedFinalField", "false");
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
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface2.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedPublicFinalAllowImpliedStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedStaticField", "false");
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
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface3.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceNoImpliedStaticFinalAllowImpliedPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicField", "false");
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
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface4.java"),
            expected);
    }

    @Test
    public void testFieldsOnInterfaceAllowImpliedPublicStaticFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicField", "false");
        checkConfig.addProperty("violateImpliedStaticField", "false");
        checkConfig.addProperty("violateImpliedFinalField", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnInterface5.java"),
            expected);
    }

    @Test
    public void testFieldsOnClassIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierFieldsOnClass.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedPublicStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
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
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnInterface.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedStaticAllowImpliedPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicNested", "false");
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "static"),
            "27:5: " + getCheckMessage(MSG_KEY, "static"),
            "35:5: " + getCheckMessage(MSG_KEY, "static"),
            "45:5: " + getCheckMessage(MSG_KEY, "static"),
            "53:5: " + getCheckMessage(MSG_KEY, "static"),
            "59:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnInterface2.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNoImpliedPublicAllowImpliedStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedStaticNested", "false");
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "40:5: " + getCheckMessage(MSG_KEY, "public"),
            "45:5: " + getCheckMessage(MSG_KEY, "public"),
            "56:5: " + getCheckMessage(MSG_KEY, "public"),
            "59:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnInterface3.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceAllowImpliedPublicStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        checkConfig.addProperty("violateImpliedPublicNested", "false");
        checkConfig.addProperty("violateImpliedStaticNested", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnInterface4.java"),
            expected);
    }

    @Test
    public void testNestedOnClassIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnClass.java"),
            expected);
    }

    @Test
    public void testNestedOnInterfaceNestedNoImpliedPublicStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY, "public"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "23:9: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig,
            getPath("InputInterfaceMemberImpliedModifierNestedOnInterfaceNested.java"),
            expected);
    }

    @Test
    public void testNestedOnClassNestedNoImpliedPublicStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY, "public"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
            "23:9: " + getCheckMessage(MSG_KEY, "public"),
            "23:9: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputInterfaceMemberImpliedModifierNestedOnClassNested.java"),
            expected);
    }

    @Test
    public void testPackageScopeInterface() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
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
        verify(checkConfig,
            getPath("InputInterfaceMemberImpliedModifierPackageScopeInterface.java"),
            expected);
    }

    @Test
    public void testPrivateMethodsOnInterface() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getNonCompilablePath("InputInterfaceMemberImpliedModifierPrivateMethods.java"),
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
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals(init.toString(), ex.getMessage(), "Error message is unexpected");
        }
    }

}
