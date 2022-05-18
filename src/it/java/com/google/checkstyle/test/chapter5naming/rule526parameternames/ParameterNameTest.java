///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ParameterNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG = "Parameter name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule526parameternames";
    }

    @Test
    public void testGeneralParameterName() throws Exception {
        final Configuration config = getModuleConfig("ParameterName");
        final String format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$";
        final String[] expected = {
            "10:21: " + getCheckMessage(MSG, "bB", format),
            "33:18: " + getCheckMessage(MSG, "llll_llll", format),
            "34:21: " + getCheckMessage(MSG, "bB", format),
            "64:13: " + getCheckMessage(MSG, "$arg1", format),
            "65:13: " + getCheckMessage(MSG, "ar$g2", format),
            "66:13: " + getCheckMessage(MSG, "arg3$", format),
            "67:13: " + getCheckMessage(MSG, "a_rg4", format),
            "68:13: " + getCheckMessage(MSG, "_arg5", format),
            "69:13: " + getCheckMessage(MSG, "arg6_", format),
            "70:13: " + getCheckMessage(MSG, "aArg7", format),
            "71:13: " + getCheckMessage(MSG, "aArg8", format),
            "72:13: " + getCheckMessage(MSG, "aar_g", format),
        };

        final String filePath = getPath("InputParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(config, filePath, expected, warnList);
    }

}
