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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedPrivateFieldCheck.MSG_UNUSED_PRIVATE_FIELD;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnusedPrivateFieldCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedprivatefield";
    }

    @Test
    public void testBasic() throws Exception {
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
            "11:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "assignedOnly"),
            "12:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "CONSTANT"),
            "13:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "staticUnused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField.java"), expected);
    }

    @Test
    public void testNestmate() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldNestmate.java"), expected);
    }

    @Test
    public void testThis() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "writeOnlyViaThis"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldThis.java"), expected);
    }

    @Test
    public void testCompound() throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldCompound.java"), expected);
    }

    @Test
    public void testAnonClass() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unusedInAnon"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldAnonClass.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedPrivateFieldCheck checkObj =
                new UnusedPrivateFieldCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.DOT,
        };
        assertWithMessage("Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnusedPrivateFieldCheck checkObj =
                new UnusedPrivateFieldCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.DOT,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testSerialVersionUid() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "otherConstant"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldSerialVersionUID.java"), expected);
    }

    @Test
    public void testSameNameInnerClasses() throws Exception {
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "count"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldSameName.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldEnum.java"), expected);
    }

    @Test
    public void testQualifiedThis() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldQualifiedThis.java"), expected);
    }

    @Test
    public void testRecord() throws Exception {
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldRecord.java"), expected);
    }

    @Test
    public void testQualifiedNew() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldQualifiedNew.java"), expected);
    }

    @Test
    public void testIllegalStateException() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.LITERAL_IF);
        getExpectedThrowable(IllegalStateException.class,
                () -> check.visitToken(ast));
    }

    @Test
    public void testAssignRhs() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldAssignRhs.java"), expected);
    }

    @Test
    public void testDotRhs() throws Exception {
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldDotRhs.java"), expected);
    }

    @Test
    public void testQualifiedThisInAnon() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_PRIVATE_FIELD, "unused"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldQualifiedThisInAnon.java"), expected);
    }
}
