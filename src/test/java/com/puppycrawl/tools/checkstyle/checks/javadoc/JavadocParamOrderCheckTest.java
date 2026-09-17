///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrderCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testParamOrder() throws Exception {
        final String[] expected = {
            "17:8: " + getCheckMessage(MSG_KEY, "p1"),
            "18:8: " + getCheckMessage(MSG_KEY, "<T>"),
            "65:8: " + getCheckMessage(MSG_KEY, "a"),
            "76:8: " + getCheckMessage(MSG_KEY, "<K>"),
            "87:8: " + getCheckMessage(MSG_KEY, "<T>"),
            "99:12: " + getCheckMessage(MSG_KEY, "name"),
            "111:8: " + getCheckMessage(MSG_KEY, "<T>"),
            "112:8: " + getCheckMessage(MSG_KEY, "p1"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParamOrder.java"), expected);
    }

    @Test
    public void testParamOrderSupplemental() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocParamOrderSupplemental.java"), expected);
    }

    @Test
    public void testCompactSource() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_KEY, "name"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputJavadocParamOrderCompact.java"),
                expected);
    }

}
