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

package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ParameterNameTest extends BaseCheckTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";
    private static String format;
    private static Configuration config;

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter5naming" + File.separator + "rule526parameternames"
                + File.separator + fileName);
    }

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        final List<Configuration> configs = getCheckConfigs("ParameterName");

        Assert.assertEquals(configs.size(), 1);

        config = configs.get(0);
        format = config.getAttribute("format");
    }

    @Test
    public void generalParameterNameTest() throws Exception {

        final String[] expected = {
            "10:21: " + getCheckMessage(config.getMessages(), MSG_KEY, "bB", format),
            "33:22: " + getCheckMessage(config.getMessages(), MSG_KEY, "llll_llll", format),
            "34:21: " + getCheckMessage(config.getMessages(), MSG_KEY, "bB", format),
            "64:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "$arg1", format),
            "65:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "ar$g2", format),
            "66:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "arg3$", format),
            "67:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "a_rg4", format),
            "68:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "_arg5", format),
            "69:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "arg6_", format),
            "70:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "aArg7", format),
            "71:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "aArg8", format),
            "72:13: " + getCheckMessage(config.getMessages(), MSG_KEY, "aar_g", format),
        };

        final String filePath = getPath("InputParameterName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(config, filePath, expected, warnList);
    }

}
