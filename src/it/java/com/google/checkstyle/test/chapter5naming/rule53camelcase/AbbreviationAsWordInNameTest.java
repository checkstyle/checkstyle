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

package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class AbbreviationAsWordInNameTest extends BaseCheckTestSupport {

    private static final String MSG_KEY = "abbreviation.as.word";
    private static ConfigurationBuilder builder;
    private static Configuration checkConfig;
    private final Class<AbbreviationAsWordInNameCheck> clazz = AbbreviationAsWordInNameCheck.class;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("AbbreviationAsWordInName");
    }

    @Test
    public void abbreviationAsWordInNameTest() throws Exception {

        final int maxCapitalCount = 1;
        final String msg = getCheckMessage(clazz, MSG_KEY, maxCapitalCount);

        final String[] expected = {
            "50: " + msg,
            "52: " + msg,
            "54: " + msg,
            "58: " + msg,
            "60: " + msg,
            "62: " + msg,
            "67: " + msg,
            "69: " + msg,
            "71: " + msg,
        };

        final String filePath = builder.getFilePath("InputAbbreviationAsWordInTypeNameCheck");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
