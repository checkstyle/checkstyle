////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

public class ClassMemberImpliedModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/classmemberimpliedmodifier";
    }

    @Test
    public void testMethodsOnClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassMemberImpliedModifierCheck.class);
        final String[] expected = {
            "42:9: " + getCheckMessage(MSG_KEY, "static"),
            "49:9: " + getCheckMessage(MSG_KEY, "static"),
            "53:5: " + getCheckMessage(MSG_KEY, "static"),
            "60:9: " + getCheckMessage(MSG_KEY, "static"),
            "67:9: " + getCheckMessage(MSG_KEY, "static"),
            "74:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputClassMemberImpliedModifierOnClass.java"),
            expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final ClassMemberImpliedModifierCheck check = new ClassMemberImpliedModifierCheck();
        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
        };
        assertArrayEquals(expected, actual, "Required tokens are invalid");
    }

    @Test
    public void testMethodsOnClassNoImpliedStaticEnum() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassMemberImpliedModifierCheck.class);
        checkConfig.addAttribute("violateImpliedStaticOnNestedEnum", "false");
        final String[] expected = {
            "50:9: " + getCheckMessage(MSG_KEY, "static"),
            "68:9: " + getCheckMessage(MSG_KEY, "static"),
            "75:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputClassMemberImpliedModifierOnClassSetEnumFalse.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNoImpliedStaticInterface() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassMemberImpliedModifierCheck.class);
        checkConfig.addAttribute("violateImpliedStaticOnNestedInterface", "false");
        final String[] expected = {
            "43:9: " + getCheckMessage(MSG_KEY, "static"),
            "54:5: " + getCheckMessage(MSG_KEY, "static"),
            "61:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputClassMemberImpliedModifierOnClassSetInterfaceFalse.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNoViolationsChecked() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassMemberImpliedModifierCheck.class);
        checkConfig.addAttribute("violateImpliedStaticOnNestedEnum", "false");
        checkConfig.addAttribute("violateImpliedStaticOnNestedInterface", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassMemberImpliedModifierOnClassNoViolations.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterface() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassMemberImpliedModifierCheck.class);
        final String[] expected = {
            "51:13: " + getCheckMessage(MSG_KEY, "static"),
            "58:13: " + getCheckMessage(MSG_KEY, "static"),
            "62:9: " + getCheckMessage(MSG_KEY, "static"),
            "69:13: " + getCheckMessage(MSG_KEY, "static"),
            "76:13: " + getCheckMessage(MSG_KEY, "static"),
            "83:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputClassMemberImpliedModifierOnInterface.java"),
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
        interfaceAst.setType(TokenTypes.CLASS_DEF);
        interfaceAst.addChild(objBlock);
        final ClassMemberImpliedModifierCheck check =
            new ClassMemberImpliedModifierCheck();
        try {
            check.visitToken(init);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals(init.toString(), ex.getMessage(), "Error message is unexpected");
        }
    }

}
