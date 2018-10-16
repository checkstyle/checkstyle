////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class AbbreviationAsWordInNameTest extends AbstractModuleTestSupport {

    private static final String MSG_KEY = "abbreviation.as.word";
    private final Class<AbbreviationAsWordInNameCheck> clazz = AbbreviationAsWordInNameCheck.class;

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule53camelcase";
    }

    @Test
    public void testAbbreviationAsWordInName() throws Exception {
        final int maxCapitalCount = 2;

        final String[] expected = {
            "52: " + getWarningMessage("supportsIPv6OnIOS", maxCapitalCount),
            "54: " + getWarningMessage("XMLHTTPRequest", maxCapitalCount),
            "60: " + getWarningMessage("supportsIPv6OnIOS", maxCapitalCount),
            "62: " + getWarningMessage("XMLHTTPRequest", maxCapitalCount),
            "69: " + getWarningMessage("supportsIPv6OnIOS", maxCapitalCount),
            "71: " + getWarningMessage("XMLHTTPRequest", maxCapitalCount),
        };

        final String filePath = getPath("InputAbbreviationAsWordInTypeNameCheck.java");

        final Configuration checkConfig = getModuleConfig("AbbreviationAsWordInName");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    private String getWarningMessage(String typeName, int expectedCapitalCount)
            throws IOException {
        return getCheckMessage(clazz, MSG_KEY, typeName, expectedCapitalCount);
    }

}
