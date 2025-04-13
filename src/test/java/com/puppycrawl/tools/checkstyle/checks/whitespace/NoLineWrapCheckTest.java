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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoLineWrapCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nolinewrap";
    }

    @Test
    public void testCaseWithoutLineWrapping() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapGood.java"), expected);
    }

    @Test
    public void testDefaultTokensLineWrapping() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "package"),
            "13:1: " + getCheckMessage(MSG_KEY, "import"),
            "17:1: " + getCheckMessage(MSG_KEY, "import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapBad.java"), expected);
    }

    @Test
    public void testCustomTokensLineWrapping()
            throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, "import"),
            "17:1: " + getCheckMessage(MSG_KEY, "import"),
            "20:1: " + getCheckMessage(MSG_KEY, "CLASS_DEF"),
            "23:9: " + getCheckMessage(MSG_KEY, "METHOD_DEF"),
            "30:1: " + getCheckMessage(MSG_KEY, "ENUM_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapBad2.java"), expected);
    }

    @Test
    public void testNoLineWrapRecordsAndCompactCtors()
            throws Exception {

        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "CTOR_DEF"),
            "19:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "28:9: " + getCheckMessage(MSG_KEY, "CTOR_DEF"),
            "34:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "36:9: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
            "40:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "42:9: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
            "47:9: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "49:13: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNoLineWrapRecordsAndCompactCtors.java"),
                expected);
    }

}
