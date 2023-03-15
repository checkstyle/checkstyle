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

package com.google.checkstyle.test.chapter2filebasic.rule233nonascii;

import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck;

public class AvoidEscapedUnicodeCharactersTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter2filebasic/rule233nonascii";
    }

    @Test
    public void testUnicodeEscapes() throws Exception {
        final String[] expected = {
            "5:42: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
            "15:38: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
            "25:36: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
            "33:38: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
            "35:38: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
            "36:47: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final Configuration checkConfig = getModuleConfig("AvoidEscapedUnicodeCharacters");
        final String filePath = getPath("InputAvoidEscapedUnicodeCharacters.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
