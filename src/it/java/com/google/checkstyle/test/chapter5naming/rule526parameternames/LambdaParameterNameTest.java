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

package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class LambdaParameterNameTest extends AbstractGoogleModuleTestSupport {

    public static final String MSG_INVALID_PATTERN = "name.invalidPattern";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule526parameternames";
    }

    @Test
    public void testLambdaParameterName() throws Exception {
        final Configuration config = getModuleConfig("LambdaParameterName");
        final String format = config.getProperty("format");
        final Map<String, String> messages = config.getMessages();

        final String[] expected = {
            "9:13: " + getCheckMessage(messages, MSG_INVALID_PATTERN, "S", format),
            "12:14: " + getCheckMessage(messages, MSG_INVALID_PATTERN, "sT", format),
            "14:65: " + getCheckMessage(messages, MSG_INVALID_PATTERN, "sT1", format),
            "14:70: " + getCheckMessage(messages, MSG_INVALID_PATTERN, "sT2", format),
            "17:21: " + getCheckMessage(messages, MSG_INVALID_PATTERN, "_s", format),
        };

        final String filePath = getPath("InputLambdaParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(config, filePath, expected, warnList);
    }

}
