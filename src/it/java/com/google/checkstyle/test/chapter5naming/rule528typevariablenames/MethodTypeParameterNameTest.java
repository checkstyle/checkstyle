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

package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class MethodTypeParameterNameTest extends AbstractModuleTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";
    private static String format;

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule528typevariablenames";
    }

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        format = getModuleConfig("ClassTypeParameterName").getAttribute("format");
    }

    @Test
    public void testMethodDefault() throws Exception {
        final Configuration checkConfig = getModuleConfig("MethodTypeParameterName");
        final Map<String, String> messages = checkConfig.getMessages();

        final String[] expected = {
            "9:6: " + getCheckMessage(messages, MSG_KEY, "e_e", format),
            "19:6: " + getCheckMessage(messages, MSG_KEY, "Tfo$o2T", format),
            "23:6: " + getCheckMessage(messages, MSG_KEY, "foo_", format),
            "28:10: " + getCheckMessage(messages, MSG_KEY, "_abc", format),
            "37:14: " + getCheckMessage(messages, MSG_KEY, "T$", format),
            "42:14: " + getCheckMessage(messages, MSG_KEY, "EE", format),
        };

        final String filePath = getPath("InputMethodTypeParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
