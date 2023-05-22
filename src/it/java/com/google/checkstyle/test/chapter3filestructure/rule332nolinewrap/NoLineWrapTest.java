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

package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoLineWrapTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule332nolinewrap";
    }

    @Test
    public void testBadLineWrap() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(NoLineWrapCheck.class, "no.line.wrap", "package"),
            "6:1: " + getCheckMessage(NoLineWrapCheck.class, "no.line.wrap", "import"),
            "10:1: " + getCheckMessage(NoLineWrapCheck.class, "no.line.wrap", "import"),
        };

        final Configuration checkConfig = getModuleConfig("NoLineWrap");
        final String filePath = getPath("InputNoLineWrapBad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testGoodLineWrap() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("NoLineWrap");
        final String filePath = getPath("InputNoLineWrapGood.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void goodLineLength() throws Exception {
        final int maxLineLength = 100;
        final String[] expected = {
            "5: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 112),
            "29: " + getCheckMessage(LineLengthCheck.class, "maxLineLen", maxLineLength, 113),
        };

        final Configuration checkConfig = getModuleConfig("LineLength");
        final String filePath = getPath("InputLineLength.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
