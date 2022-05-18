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

public class LocalVariableNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG = "Local variable name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule527localvariablenames";
    }

    @Test
    public void testLocalVariableName() throws Exception {
        final Configuration checkConfig = getModuleConfig("LocalVariableName");
        final String format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$";
        final String[] expected = {
            "27:13: " + getCheckMessage(MSG, "aA", format),
            "28:13: " + getCheckMessage(MSG, "a1_a", format),
            "29:13: " + getCheckMessage(MSG, "A_A", format),
            "30:13: " + getCheckMessage(MSG, "aa2_a", format),
            "31:13: " + getCheckMessage(MSG, "_a", format),
            "32:13: " + getCheckMessage(MSG, "_aa", format),
            "33:13: " + getCheckMessage(MSG, "aa_", format),
            "34:13: " + getCheckMessage(MSG, "aaa$aaa", format),
            "35:13: " + getCheckMessage(MSG, "$aaaaaa", format),
            "36:13: " + getCheckMessage(MSG, "aaaaaa$", format),
        };

        final String filePath = getPath("InputLocalVariableNameSimple.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testOneChar() throws Exception {
        final Configuration checkConfig = getModuleConfig("LocalVariableName");
        final String format = "^[a-z]([a-z0-9][a-zA-Z0-9]*)?$";
        final String[] expected = {
            "21:17: " + getCheckMessage(MSG, "I_ndex", format),
            "45:17: " + getCheckMessage(MSG, "i_ndex", format),
            "49:17: " + getCheckMessage(MSG, "ii_i1", format),
            "53:17: " + getCheckMessage(MSG, "$index", format),
            "57:17: " + getCheckMessage(MSG, "in$dex", format),
            "61:17: " + getCheckMessage(MSG, "index$", format),
        };

        final String filePath = getPath("InputLocalVariableNameOneCharVarName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
