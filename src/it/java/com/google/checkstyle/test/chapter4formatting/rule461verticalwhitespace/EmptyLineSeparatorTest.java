////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck;

public class EmptyLineSeparatorTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule461verticalwhitespace";
    }

    @Test
    public void testEmptyLineSeparator() throws Exception {
        final Class<EmptyLineSeparatorCheck> clazz = EmptyLineSeparatorCheck.class;
        final String messageKey = "empty.line.separator";

        final String[] expected = {
            "19: " + getCheckMessage(clazz, messageKey, "package"),
            "20: " + getCheckMessage(clazz, messageKey, "import"),
            "33: " + getCheckMessage(clazz, messageKey, "CLASS_DEF"),
            "37: " + getCheckMessage(clazz, messageKey, "STATIC_INIT"),
            "66: " + getCheckMessage(clazz, messageKey, "METHOD_DEF"),
            "75: " + getCheckMessage(clazz, messageKey, "INTERFACE_DEF"),
            "82: " + getCheckMessage(clazz, messageKey, "INSTANCE_INIT"),
            "113: " + getCheckMessage(clazz, messageKey, "CLASS_DEF"),
            "119: " + getCheckMessage(clazz, messageKey, "VARIABLE_DEF"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyLineSeparator");
        final String filePath = getPath("InputEmptyLineSeparator.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
