///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        final String[] expected = {
            "51:9: " + getCheckMessage(MSG_KEY, "static"),
            "58:9: " + getCheckMessage(MSG_KEY, "static"),
            "62:5: " + getCheckMessage(MSG_KEY, "static"),
            "69:9: " + getCheckMessage(MSG_KEY, "static"),
            "76:9: " + getCheckMessage(MSG_KEY, "static"),
            "83:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassMemberImpliedModifierOnClass.java"),
            expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final ClassMemberImpliedModifierCheck check = new ClassMemberImpliedModifierCheck();
        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testMethodsOnClassNoImpliedStaticEnum() throws Exception {
        final String[] expected = {
            "59:9: " + getCheckMessage(MSG_KEY, "static"),
            "77:9: " + getCheckMessage(MSG_KEY, "static"),
            "84:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassMemberImpliedModifierOnClassSetEnumFalse.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNoImpliedStaticInterface() throws Exception {
        final String[] expected = {
            "52:9: " + getCheckMessage(MSG_KEY, "static"),
            "63:5: " + getCheckMessage(MSG_KEY, "static"),
            "70:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassMemberImpliedModifierOnClassSetInterfaceFalse.java"),
            expected);
    }

    @Test
    public void testMethodsOnClassNoViolationsChecked() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassMemberImpliedModifierOnClassNoViolations.java"),
            expected);
    }

    @Test
    public void testMethodsOnInterface() throws Exception {
        final String[] expected = {
            "60:13: " + getCheckMessage(MSG_KEY, "static"),
            "67:13: " + getCheckMessage(MSG_KEY, "static"),
            "71:9: " + getCheckMessage(MSG_KEY, "static"),
            "78:13: " + getCheckMessage(MSG_KEY, "static"),
            "85:13: " + getCheckMessage(MSG_KEY, "static"),
            "92:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassMemberImpliedModifierOnInterface.java"),
            expected);
    }

    @Test
    public void testClassMemberImpliedModifierRecords() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, "static"),
            "20:5: " + getCheckMessage(MSG_KEY, "static"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "29:9: " + getCheckMessage(MSG_KEY, "static"),
            "33:9: " + getCheckMessage(MSG_KEY, "static"),
            "37:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputClassMemberImpliedModifierRecords.java"),
                expected);
    }

    @Test
    public void testClassMemberImpliedModifierNoViolationRecords() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, "static"),
            "20:5: " + getCheckMessage(MSG_KEY, "static"),
            "33:9: " + getCheckMessage(MSG_KEY, "static"),
            "37:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputClassMemberImpliedModifierNoViolationRecords.java"),
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
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(init.toString());
        }
    }

}
