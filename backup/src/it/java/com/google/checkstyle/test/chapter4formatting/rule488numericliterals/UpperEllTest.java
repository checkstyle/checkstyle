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

package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class UpperEllTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule488numericliterals";
    }

    @Test
    public void testUpperEll() throws Exception {
        final String[] expected = {
            "6:33: Should use uppercase 'L'.",
            "12:25: Should use uppercase 'L'.",
            "14:30: Should use uppercase 'L'.",
            "17:16: Should use uppercase 'L'.",
            "21:27: Should use uppercase 'L'.",
            "22:20: Should use uppercase 'L'.",
            "25:13: Should use uppercase 'L'.",
            "34:44: Should use uppercase 'L'.",
            "40:29: Should use uppercase 'L'.",
            "42:34: Should use uppercase 'L'.",
            "45:20: Should use uppercase 'L'.",
            "50:31: Should use uppercase 'L'.",
            "51:24: Should use uppercase 'L'.",
            "56:21: Should use uppercase 'L'.",
            "65:45: Should use uppercase 'L'.",
            "71:37: Should use uppercase 'L'.",
            "73:42: Should use uppercase 'L'.",
            "76:28: Should use uppercase 'L'.",
            "80:39: Should use uppercase 'L'.",
            "81:32: Should use uppercase 'L'.",
            "84:25: Should use uppercase 'L'.",
            "97:43: Should use uppercase 'L'.",
            "99:27: Should use uppercase 'L'.",
            "100:20: Should use uppercase 'L'.",
        };

        final Configuration checkConfig = getModuleConfig("UpperEll");
        final String filePath = getPath("InputUpperEll.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
