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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryPermitsClauseCheck.MSG_PERMIT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnnecessaryPermitsClauseCheckTest extends AbstractModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarypermitsclause";
    }

    @Test
    public void testNonSealedClasses() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseNonSealedClasses.java"),
                expected);
    }

    @Test
    public void testSealedClasses() throws Exception {
        final String[] expected = {
            "13:22: " + getCheckMessage(MSG_PERMIT, "SealedA"),
            "23:26: " + getCheckMessage(MSG_PERMIT, "SealedI"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseSealedClasses.java"),
                expected);
    }

    @Test
    public void testInnerClasses() throws Exception {
        final String[] expected = {
            "12:31: " + getCheckMessage(MSG_PERMIT, "SealedOuter1"),
            "28:38: " + getCheckMessage(MSG_PERMIT, "SealedInterface"),
            "45:31: " + getCheckMessage(MSG_PERMIT, "SealedOuter4"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseInnerClasses.java"),
                expected);
    }

    @Test
    public void testUnorderedClasses() throws Exception {
        final String[] expected = {
            "19:26: " + getCheckMessage(MSG_PERMIT, "SealedLater"),
            "26:28: " + getCheckMessage(MSG_PERMIT, "SealedPartial"),
            "37:34: " + getCheckMessage(MSG_PERMIT, "SealedInterface"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseUnorderedClasses.java"),
                expected);
    }

    @Test
    public void testSubClassesInDifferentFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseSubClassesInAnotherFile.java"),
                getPath("InputUnnecessaryPermitsClauseSubClassesInAnotherFile2.java"),
                expected);
    }

    @Test
    public void testSubClassExtendsAndPermits() throws Exception {
        final String[] expected = {
            "12:25: " + getCheckMessage(MSG_PERMIT, "Parent"),
            "16:38: " + getCheckMessage(MSG_PERMIT, "Base"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseExtendsAndPermits.java"),
                expected);
    }

}
