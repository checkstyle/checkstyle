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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryTypeArgumentsWithRecordPatternCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class UnnecessaryTypeArgumentsWithRecordPatternCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks"
                + "/coding/unnecessarytypeargumentswithrecordpattern";
    }

    @Test
    public void testUnnecessaryTypeArgumentsWithRecordPatternOne() throws Exception {
        final String[] expected = {
            "16:31: " + getCheckMessage(MSG_KEY),
            "16:48: " + getCheckMessage(MSG_KEY),
            "20:31: " + getCheckMessage(MSG_KEY),
            "45:20: " + getCheckMessage(MSG_KEY),
            "45:37: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryTypeArgumentsWithRecordPatternOne.java"), expected);
    }

    @Test
    public void testUnnecessaryTypeArgumentsWithRecordPatternMultiple() throws Exception {
        final String[] expected = {
            "14:33: " + getCheckMessage(MSG_KEY),
            "22:33: " + getCheckMessage(MSG_KEY),
            "30:22: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryTypeArgumentsWithRecordPatternMultiple.java"), expected);
    }

    @Test
    public void testUnnecessaryTypeArgumentsWithRecordPatternNested() throws Exception {
        final String[] expected = {
            "17:31: " + getCheckMessage(MSG_KEY),
            "17:48: " + getCheckMessage(MSG_KEY),
            "21:31: " + getCheckMessage(MSG_KEY),
            "35:21: " + getCheckMessage(MSG_KEY),
            "35:38: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryTypeArgumentsWithRecordPatternNested.java"), expected);
    }

}
