///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.ParameterAlignmentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParameterAlignmentCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/parameteralignment";
    }

    @Test
    public void testGetRequiredTokens() {
        final ParameterAlignmentCheck checkObj = new ParameterAlignmentCheck();
        assertWithMessage("ParameterAlignmentCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterAlignmentCheck paramNumberCheckObj =
            new ParameterAlignmentCheck();
        final int[] actual = paramNumberCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.METHOD_CALL,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.ANNOTATION,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testCtor()
            throws Exception {
        final String[] expected = {
            "24:29: " + getCheckMessage(MSG_KEY),
            "25:29: " + getCheckMessage(MSG_KEY),
            "36:9: " + getCheckMessage(MSG_KEY),
            "42:9: " + getCheckMessage(MSG_KEY),
            "48:9: " + getCheckMessage(MSG_KEY),
            "50:13: " + getCheckMessage(MSG_KEY),
            "92:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterAlignmentCtor.java"), expected);
    }

    @Test
    public void testMethod()
            throws Exception {
        final String[] expected = {
            "41:13: " + getCheckMessage(MSG_KEY),
            "46:9: " + getCheckMessage(MSG_KEY),
            "51:9: " + getCheckMessage(MSG_KEY),
            "53:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterAlignmentMethod.java"), expected);
    }

    @Test
    public void testRecord()
            throws Exception {
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY),
            "35:9: " + getCheckMessage(MSG_KEY),
            "40:9: " + getCheckMessage(MSG_KEY),
            "42:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParameterAlignmentRecord.java"), expected);
    }

    @Test
    public void testAnnotation()
            throws Exception {
        final String[] expected = {
            "61:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterAlignmentAnnotation.java"), expected);
    }

}
