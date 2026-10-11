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

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck;

public class SeverityMatchFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/severitymatchfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "21:7: " + getCheckMessage(VisibilityModifierCheck.class,
                    VisibilityModifierCheck.MSG_KEY, "field1"),
            "24:27: " + getCheckMessage(ParameterNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "V1", pattern),
            "27:15: " + getCheckMessage(MethodNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "Method2", pattern),
        };

        final String[] expectedWithFilter = {
            "27:15: " + getCheckMessage(MethodNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "Method2", pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "23:7: " + getCheckMessage(VisibilityModifierCheck.class,
                    VisibilityModifierCheck.MSG_KEY, "field1"),
            "26:27: " + getCheckMessage(ParameterNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "V1", pattern),
            "29:15: " + getCheckMessage(MethodNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "Method2", pattern),
        };

        final String[] expectedWithFilter = {
            "26:27: " + getCheckMessage(ParameterNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "V1", pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "24:7: " + getCheckMessage(VisibilityModifierCheck.class,
                    VisibilityModifierCheck.MSG_KEY, "field1"),
            "27:27: " + getCheckMessage(ParameterNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "V1", pattern),
            "30:15: " + getCheckMessage(MethodNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "Method2", pattern),
        };

        final String[] expectedWithFilter = {
            "27:27: " + getCheckMessage(ParameterNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "V1", pattern),
            "30:15: " + getCheckMessage(MethodNameCheck.class,
                    ParameterNameCheck.MSG_INVALID_PATTERN,
                    "Method2", pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

}
