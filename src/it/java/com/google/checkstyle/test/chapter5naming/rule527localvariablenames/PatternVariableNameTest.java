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

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class PatternVariableNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG = "Pattern variable name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule527localvariablenames";
    }

    @Test
    public void testPatternVariableName() throws Exception {
        final Configuration checkConfig = getModuleConfig("PatternVariableName");
        final String format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$";
        final String[] expected = {
            "11:39: " + getCheckMessage(MSG, "OTHER", format),
            "21:34: " + getCheckMessage(MSG, "Count", format),
            "36:36: " + getCheckMessage(MSG, "aA", format),
            "37:42: " + getCheckMessage(MSG, "a1_a", format),
            "40:34: " + getCheckMessage(MSG, "A_A", format),
            "41:43: " + getCheckMessage(MSG, "aa2_a", format),
            "53:37: " + getCheckMessage(MSG, "_a", format),
            "59:43: " + getCheckMessage(MSG, "_aa", format),
            "63:41: " + getCheckMessage(MSG, "aa_", format),
            "68:38: " + getCheckMessage(MSG, "aaa$aaa", format),
            "69:36: " + getCheckMessage(MSG, "$aaaaaa", format),
            "70:37: " + getCheckMessage(MSG, "aaaaaa$", format),
            "77:41: " + getCheckMessage(MSG, "_A_aa_B", format),

        };

        final String filePath =
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofTestDefault.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
