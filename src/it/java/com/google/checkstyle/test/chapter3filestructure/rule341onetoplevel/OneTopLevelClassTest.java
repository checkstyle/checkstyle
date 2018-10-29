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

package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneTopLevelClassTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule341onetoplevel";
    }

    @Test
    public void testBad() throws Exception {
        final Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        final String messageKey = "one.top.level.class";

        final String[] expected = {
            "25: " + getCheckMessage(clazz, messageKey, "NoSuperClone"),
            "33: " + getCheckMessage(clazz, messageKey, "InnerClone"),
            "50: " + getCheckMessage(clazz, messageKey, "CloneWithTypeArguments"),
            "55: " + getCheckMessage(clazz, messageKey, "CloneWithTypeArgumentsAndNoSuper"),
            "60: " + getCheckMessage(clazz, messageKey, "MyClassWithGenericSuperMethod"),
            "77: " + getCheckMessage(clazz, messageKey, "AnotherClass"),
        };

        final Configuration checkConfig = getModuleConfig("OneTopLevelClass");
        final String filePath = getPath("InputOneTopLevelClassBasic.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testGood() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("OneTopLevelClass");
        final String filePath = getPath("InputOneTopLevelClassGood.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testBad1() throws Exception {
        final Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        final String messageKey = "one.top.level.class";

        final String[] expected = {
            "4: " + getCheckMessage(clazz, messageKey, "FooEnum"),
        };

        final Configuration checkConfig = getModuleConfig("OneTopLevelClass");
        final String filePath = getPath("InputOneTopLevelClassBad1.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testBad2() throws Exception {
        final Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        final String messageKey = "one.top.level.class";

        final String[] expected = {
            "5: " + getCheckMessage(clazz, messageKey, "FooIn"),
            "7: " + getCheckMessage(clazz, messageKey, "FooClass"),
        };

        final Configuration checkConfig = getModuleConfig("OneTopLevelClass");
        final String filePath = getPath("InputOneTopLevelClassBad2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
