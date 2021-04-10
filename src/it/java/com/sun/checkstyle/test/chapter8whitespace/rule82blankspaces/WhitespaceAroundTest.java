////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck;
import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class WhitespaceAroundTest
    extends AbstractSunModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter8whitespace/rule82blankspaces";
    }

    @Test
    public void testSimpleInput()
            throws Exception {
        final Configuration checkConfig = getModuleConfig("WhitespaceAround");
        final String[] expected = {
            "153:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
            "154:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
            "155:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
            "156:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
            "157:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
            "158:26: " + getCheckMessage(WhitespaceAroundCheck.class, "ws.notFollowed", "="),
        };
        final String filePath = getPath("InputWhitespaceAroundSimple.java");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
