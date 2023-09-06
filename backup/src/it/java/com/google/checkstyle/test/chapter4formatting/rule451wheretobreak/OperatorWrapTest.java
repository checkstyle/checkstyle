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

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck;

public class OperatorWrapTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule451wheretobreak";
    }

    @Test
    public void testOperatorWrap() throws Exception {
        final Class<OperatorWrapCheck> clazz = OperatorWrapCheck.class;
        final String messageKey = "line.new";

        final String[] expected = {
            "11:27: " + getCheckMessage(clazz, messageKey, "+"),
            "12:28: " + getCheckMessage(clazz, messageKey, "-"),
            "20:27: " + getCheckMessage(clazz, messageKey, "&&"),
            "28:33: " + getCheckMessage(clazz, messageKey, "::"),
            "43:35: " + getCheckMessage(clazz, messageKey, "&"),
            "62:42: " + getCheckMessage(clazz, messageKey, "?"),
            "66:27: " + getCheckMessage(clazz, messageKey, "!="),
            "72:30: " + getCheckMessage(clazz, messageKey, "=="),
            "78:27: " + getCheckMessage(clazz, messageKey, ">"),
            "84:35: " + getCheckMessage(clazz, messageKey, "||"),
            "107:46: " + getCheckMessage(clazz, messageKey, "?"),
            "111:31: " + getCheckMessage(clazz, messageKey, "!="),
            "117:34: " + getCheckMessage(clazz, messageKey, "=="),
            "123:31: " + getCheckMessage(clazz, messageKey, ">"),
            "129:39: " + getCheckMessage(clazz, messageKey, "||"),
            "153:46: " + getCheckMessage(clazz, messageKey, "?"),
            "157:31: " + getCheckMessage(clazz, messageKey, "!="),
            "163:34: " + getCheckMessage(clazz, messageKey, "=="),
            "169:31: " + getCheckMessage(clazz, messageKey, ">"),
            "175:39: " + getCheckMessage(clazz, messageKey, "||"),
            "194:38: " + getCheckMessage(clazz, messageKey, "?"),
        };

        final Configuration checkConfig = getModuleConfig("OperatorWrap");
        final String filePath = getPath("InputOperatorWrap.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
