////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule51identifiernames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class CatchParameterNameTest extends BaseCheckTestSupport {
    private static Configuration checkConfig;
    private static String format;

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter5naming" + File.separator + "rule51identifiernames"
            + File.separator + fileName);
    }

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        checkConfig = getCheckConfig("CatchParameterName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void catchParameterNameTest() throws Exception {
        final String msgKey = "name.invalidPattern";

        final String[] expected = {
            "47:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "iException", format),
            "50:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "ex_1", format),
            "53:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "eX", format),
            "56:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "eXX", format),
            "59:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "x_y_z", format),
            "62:28: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Ex", format),
        };

        final String filePath = getPath("InputCatchParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
