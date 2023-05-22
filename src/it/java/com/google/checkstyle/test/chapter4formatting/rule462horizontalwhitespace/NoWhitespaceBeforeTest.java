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

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceBeforeTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testEmptyForLoop() throws Exception {
        final Class<NoWhitespaceBeforeCheck> clazz = NoWhitespaceBeforeCheck.class;
        final String messageKeyPreceded = "ws.preceded";

        final String[] expected = {
            "12:24: " + getCheckMessage(clazz, messageKeyPreceded, ";"),
            "18:32: " + getCheckMessage(clazz, messageKeyPreceded, ";"),
        };
        final Configuration checkConfig = getModuleConfig("NoWhitespaceBefore");
        final String filePath = getPath("InputNoWhitespaceBeforeEmptyForLoop.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testColonOfLabel() throws Exception {
        final Class<NoWhitespaceBeforeCheck> clazz = NoWhitespaceBeforeCheck.class;
        final String messageKeyPreceded = "ws.preceded";

        final String[] expected = {
            "6:16: " + getCheckMessage(clazz, messageKeyPreceded, ":"),
        };
        final Configuration checkConfig = getModuleConfig("NoWhitespaceBefore");
        final String filePath = getPath("InputNoWhitespaceBeforeColonOfLabel.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Configuration checkConfig = getModuleConfig("NoWhitespaceBefore");
        final String filePath = getPath("InputNoWhitespaceBeforeAnnotations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}

