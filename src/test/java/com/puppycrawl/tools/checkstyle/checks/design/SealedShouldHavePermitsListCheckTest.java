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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.SealedShouldHavePermitsListCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SealedShouldHavePermitsListCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/sealedshouldhavepermitslist";
    }

    @Test
    public void testGetRequiredTokens() {
        final SealedShouldHavePermitsListCheck checkObj = new SealedShouldHavePermitsListCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
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
    public void testInnerSealedClass() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY),
            "15:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSealedShouldHavePermitsListInnerClass.java"),
                expected);
    }

    @Test
    public void testInnerSealedInterface() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSealedShouldHavePermitsListInnerInterface.java"),
                expected);
    }

    @Test
    public void testTopLevelSealedClass() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSealedShouldHavePermitsListTopLevelSealedClass.java"),
                expected);
    }

    @Test
    public void testTopLevelSealedInterface() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputSealedShouldHavePermitsListTopLevelSealedInterface.java"),
                expected);
    }

    @Test
    public void testJepExample() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY),
            "24:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputSealedShouldHavePermitsListJepExample.java"),
                expected);
    }
}
