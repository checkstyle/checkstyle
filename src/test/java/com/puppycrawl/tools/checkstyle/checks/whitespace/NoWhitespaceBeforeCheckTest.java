///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceBeforeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespacebefore";
    }

    @Test
    // make work then replace with #testInputNoWhitespaceBefore22
    @Disabled
    public void testInputNoWhitespaceBefore() throws Exception {
        verifyWithInlineConfigParser(
            getPath("InputNoWhitespaceBefore.java"), new String[] {
                "11:13: " + getCheckMessage(MSG_KEY, ".")
            });
    }

    @Test
    // make work
    @Disabled
    public void testInputNoWhitespaceBefore22() throws Exception {
        verifyWithInlineConfigParser(
            getPath("InputNoWhitespaceBefore22.java"), new String[] {
                "18:13: " + getCheckMessage(MSG_KEY, "."),
                "19:13: " + getCheckMessage(MSG_KEY, "."),
                "20:13: " + getCheckMessage(MSG_KEY, "."),
                "21:13: " + getCheckMessage(MSG_KEY, "."),
                "22:13: " + getCheckMessage(MSG_KEY, "."),
                "23:13: " + getCheckMessage(MSG_KEY, "."),
                "24:13: " + getCheckMessage(MSG_KEY, "."),
                "26:13: " + getCheckMessage(MSG_KEY, "."),
                "27:13: " + getCheckMessage(MSG_KEY, "."),
                "28:13: " + getCheckMessage(MSG_KEY, "."),
                "31:13: " + getCheckMessage(MSG_KEY, "."),
                "32:13: " + getCheckMessage(MSG_KEY, "."),
                "33:13: " + getCheckMessage(MSG_KEY, "."),
                "34:13: " + getCheckMessage(MSG_KEY, "."),
                "35:13: " + getCheckMessage(MSG_KEY, "."),
                "36:13: " + getCheckMessage(MSG_KEY, "."),
            });
    }

}
