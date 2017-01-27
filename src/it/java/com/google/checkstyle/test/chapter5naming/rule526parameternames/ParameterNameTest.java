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
    private static String genealFormat;
    private static String pubFormat;
    private static Configuration generalConfig;
    private static Configuration pubConfig;

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter5naming" + File.separator + "rule526parameternames"
                + File.separator + fileName);
    }

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        final List<Configuration> configs = getCheckConfigs("ParameterName");

        Assert.assertEquals(configs.size(), 2);

        generalConfig = configs.get(0);
        Assert.assertEquals(generalConfig.getAttribute("accessModifiers"),
            "protected, package, private");
        genealFormat = generalConfig.getAttribute("format");

        pubConfig = configs.get(1);
        Assert.assertEquals(pubConfig.getAttribute("accessModifiers"), "public");
        pubFormat = pubConfig.getAttribute("format");
    }

    @Test
    public void generalParameterNameTest() throws Exception {

        final String[] expected = {
            "8:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "$arg1", genealFormat),
            "9:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "ar$g2", genealFormat),
            "10:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "arg3$", genealFormat),
            "11:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "a_rg4", genealFormat),
            "12:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "_arg5", genealFormat),
            "13:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "arg6_", genealFormat),
            "14:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "aArg7", genealFormat),
            "15:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "aArg8", genealFormat),
            "16:21: "
                + getCheckMessage(generalConfig.getMessages(), MSG_KEY, "aar_g", genealFormat),
        };

        final String filePath = getPath("InputParameterNameSimpleGeneral.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(generalConfig, filePath, expected, warnList);
    }

    @Test
    public void pubParameterNameTest() throws Exception {

        final String[] expected = {
            "10:21: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "bB", pubFormat),
            "33:22: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "llll_llll", pubFormat),
            "34:21: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "bB", pubFormat),
            "44:23: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "p", pubFormat),
            "53:31: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "p", pubFormat),
            "58:44: " + getCheckMessage(pubConfig.getMessages(), MSG_KEY, "p", pubFormat),
        };

        final String filePath = getPath("InputParameterNameSimplePub.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(pubConfig, filePath, expected, warnList);
    }
}
