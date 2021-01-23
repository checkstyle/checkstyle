////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class RecordTypeParameterNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule528typevariablenames";
    }

    @Test
    public void testRecordDefault() throws Exception {
        final Configuration configuration = getModuleConfig("RecordTypeParameterName");
        final String format = configuration.getAttribute("format");
        final Map<String, String> messages = configuration.getMessages();

        final String[] expected = {
            "13:44: " + getCheckMessage(messages, MSG_KEY, "t", format),
            "20:15: " + getCheckMessage(messages, MSG_KEY, "foo", format),
            "35:25: " + getCheckMessage(messages, MSG_KEY, "foo", format),
        };

        final String filePath = getNonCompilablePath("InputRecordTypeParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(configuration, filePath, expected, warnList);
    }

}
