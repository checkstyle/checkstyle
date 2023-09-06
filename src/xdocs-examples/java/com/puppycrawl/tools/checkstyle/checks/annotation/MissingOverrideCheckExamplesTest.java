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

import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;


public class MissingOverrideCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/missingoverride";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
                "32:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE, "test2"),
                "37:5: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "test3"),
                "42:5: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "test4"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
                "16:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE, "equals"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}
