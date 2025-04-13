///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UniquePropertiesCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/uniqueproperties";
    }

    @Test
    public void testExample1() throws Exception {
        final String configFilePath = getPath("Example1.java");
        final String propertyFilePath = getPath("Example1.properties");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_KEY, "key.one", 2),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                propertyFilePath, expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String configFilePath = getPath("Example2.java");
        final String propertyFilePath = getPath("Example2.customProperties");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_KEY, "key.one", 2),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                propertyFilePath, expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String configFilePath = getPath("Example2.java");
        final String propertyFilePath = getPath("Example3.properties");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                propertyFilePath, expected);
    }
}
