////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule521packagenames;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class PackageNameTest extends BaseCheckTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";
    private static ConfigurationBuilder builder;
    private static Configuration checkConfig;
    private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("PackageName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void goodPackageNameTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        final String filePath = builder.getFilePath("InputPackageNameGood");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageNameTest() throws Exception {

        final String packagePath =
                "com.google.checkstyle.test.chapter5naming.rule521packageNamesCamelCase";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = builder.getFilePath("InputPackageNameBad");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageName2Test() throws Exception {

        final String packagePath = "com.google.checkstyle.test.chapter5naming.rule521_packagenames";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = builder.getFilePath("InputBadPackageName2");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageName3Test() throws Exception {

        final String packagePath = "com.google.checkstyle.test.chapter5naming.rule521$packagenames";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = builder.getFilePath("InputPackageBadName3");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
