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

package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import java.util.Map;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class MethodNameTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule523methodnames";
    }

    @Test
    public void testMethodName() throws Exception {
        final Configuration checkConfig = getModuleConfig("MethodName");
        final String msgKey = "name.invalidPattern";
        final String format = "^[a-z][a-z0-9][a-zA-Z0-9_]*$";
        final Map<String, String> messages = checkConfig.getMessages();

        final String[] expected = {
            "11:14: " + getCheckMessage(messages, msgKey, "Foo", format),
            "12:14: " + getCheckMessage(messages, msgKey, "fOo", format),
            "14:14: " + getCheckMessage(messages, msgKey, "f$o", format),
            "15:14: " + getCheckMessage(messages, msgKey, "f_oo", format),
            "16:14: " + getCheckMessage(messages, msgKey, "f", format),
            "17:14: " + getCheckMessage(messages, msgKey, "fO", format),
            "21:22: " + getCheckMessage(messages, msgKey, "Foo", format),
            "22:22: " + getCheckMessage(messages, msgKey, "fOo", format),
            "24:22: " + getCheckMessage(messages, msgKey, "f$o", format),
            "25:22: " + getCheckMessage(messages, msgKey, "f_oo", format),
            "26:22: " + getCheckMessage(messages, msgKey, "f", format),
            "27:22: " + getCheckMessage(messages, msgKey, "fO", format),
            "32:22: " + getCheckMessage(messages, msgKey, "Foo", format),
            "33:22: " + getCheckMessage(messages, msgKey, "fOo", format),
            "35:22: " + getCheckMessage(messages, msgKey, "f$o", format),
            "36:22: " + getCheckMessage(messages, msgKey, "f_oo", format),
            "37:22: " + getCheckMessage(messages, msgKey, "f", format),
            "38:22: " + getCheckMessage(messages, msgKey, "fO", format),
            "44:14: " + getCheckMessage(messages, msgKey, "Foo", format),
            "45:14: " + getCheckMessage(messages, msgKey, "fOo", format),
            "47:14: " + getCheckMessage(messages, msgKey, "f$o", format),
            "48:14: " + getCheckMessage(messages, msgKey, "f_oo", format),
            "49:14: " + getCheckMessage(messages, msgKey, "f", format),
            "50:14: " + getCheckMessage(messages, msgKey, "fO", format),
        };

        final String filePath = getPath("InputMethodName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
