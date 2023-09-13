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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SuppressWarningsCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/suppresswarnings";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {

            "13:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "16:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "40:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "44:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "49:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}
