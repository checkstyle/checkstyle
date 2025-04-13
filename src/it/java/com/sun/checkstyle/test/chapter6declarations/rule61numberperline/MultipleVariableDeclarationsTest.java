///
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
///

package com.sun.checkstyle.test.chapter6declarations.rule61numberperline;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;
import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class MultipleVariableDeclarationsTest extends AbstractSunModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter6declarations/rule61numberperline";
    }

    @Test
    public void testMultipleVariableDeclarations() throws Exception {
        final String msgComma = getCheckMessage(MultipleVariableDeclarationsCheck.class,
            "multiple.variable.declarations.comma");
        final String msg = getCheckMessage(MultipleVariableDeclarationsCheck.class,
            "multiple.variable.declarations");

        final String[] expected = {
            "5:5: " + msgComma,
            "6:5: " + msg,
            "9:9: " + msgComma,
            "10:9: " + msg,
            "14:5: " + msg,
            "17:5: " + msg,
            "31:9: " + msgComma,
            "32:9: " + msg,
            "35:13: " + msgComma,
            "36:13: " + msg,
            "40:9: " + msg,
            "43:9: " + msg,
            "57:13: " + msgComma,
            "58:13: " + msg,
            "61:17: " + msgComma,
            "62:17: " + msg,
            "66:13: " + msg,
            "69:13: " + msg,
            "86:5: " + msgComma,
            "89:5: " + msgComma,
        };

        final Configuration checkConfig = getModuleConfig("MultipleVariableDeclarations");
        final String filePath = getPath("InputMultipleVariableDeclarations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
