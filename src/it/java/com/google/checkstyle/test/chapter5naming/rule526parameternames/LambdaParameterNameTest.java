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

public class LambdaParameterNameTest extends AbstractGoogleModuleTestSupport {

    public static final String MSG = "Lambda parameter name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule526parameternames";
    }

    @Test
    public void testLambdaParameterName() throws Exception {
        final Configuration config = getModuleConfig("LambdaParameterName");
        final String format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$";

        final String[] expected = {
            "9:13: " + getCheckMessage(MSG, "S", format),
            "12:14: " + getCheckMessage(MSG, "sT", format),
            "14:65: " + getCheckMessage(MSG, "sT1", format),
            "14:70: " + getCheckMessage(MSG, "sT2", format),
            "17:21: " + getCheckMessage(MSG, "_s", format),
        };

        final String filePath = getPath("InputLambdaParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(config, filePath, expected, warnList);
    }

}
