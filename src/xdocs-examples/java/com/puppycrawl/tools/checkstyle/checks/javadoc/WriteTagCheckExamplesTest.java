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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WriteTagCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/writetag";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(WriteTagCheck.MSG_MISSING_TAG, "@since"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(WriteTagCheck.MSG_MISSING_TAG, "@since"),
            "24: " + getCheckMessage(WriteTagCheck.MSG_WRITE_TAG, "@since", ""),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String pattern = "[1-9\\.]";
        final String[] expected = {
            "26: " + getCheckMessage(WriteTagCheck.MSG_TAG_FORMAT, "@since", pattern),
            "31: " + getCheckMessage(WriteTagCheck.MSG_MISSING_TAG, "@since"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String pattern = "[1-9\\.]";
        final String[] expected = {
            "21: " + getCheckMessage(WriteTagCheck.MSG_WRITE_TAG, "@since", "1.2"),
            "27: " + getCheckMessage(WriteTagCheck.MSG_TAG_FORMAT, "@since", pattern),
            "32: " + getCheckMessage(WriteTagCheck.MSG_MISSING_TAG, "@since"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}
