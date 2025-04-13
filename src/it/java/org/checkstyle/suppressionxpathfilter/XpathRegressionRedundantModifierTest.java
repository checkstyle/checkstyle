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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck;

public class XpathRegressionRedundantModifierTest extends AbstractXpathTestSupport {

    private final String checkName = RedundantModifierCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRedundantModifierClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RedundantModifierCheck.class);

        final String[] expected = {
            "7:10: " + getCheckMessage(RedundantModifierCheck.class,
                    RedundantModifierCheck.MSG_KEY,
                    "final"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierClass']]"
                   + "/OBJBLOCK/CLASS_DEF"
                   + "[./IDENT[@text='Example1']]"
                   + "/OBJBLOCK/METHOD_DEF"
                   + "[./IDENT[@text='test']]"
                   + "/SLIST/LITERAL_TRY/RESOURCE_SPECIFICATION/RESOURCES",

                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierClass']]"
                   + "/OBJBLOCK/CLASS_DEF"
                   + "[./IDENT[@text='Example1']]"
                   + "/OBJBLOCK/METHOD_DEF"
                   + "[./IDENT[@text='test']]"
                   + "/SLIST/LITERAL_TRY/RESOURCE_SPECIFICATION/RESOURCES"
                   + "/RESOURCE"
                   + "[./IDENT[@text='a']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierClass']]"
                   + "/OBJBLOCK/CLASS_DEF"
                   + "[./IDENT[@text='Example1']]"
                   + "/OBJBLOCK/METHOD_DEF"
                   + "[./IDENT[@text='test']]"
                   + "/SLIST/LITERAL_TRY/RESOURCE_SPECIFICATION/RESOURCES"
                   + "/RESOURCE"
                   + "[./IDENT[@text='a']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierClass']]"
                   + "/OBJBLOCK/CLASS_DEF"
                   + "[./IDENT[@text='Example1']]"
                   + "/OBJBLOCK/METHOD_DEF"
                   + "[./IDENT[@text='test']]"
                   + "/SLIST/LITERAL_TRY/RESOURCE_SPECIFICATION/RESOURCES"
                   + "/RESOURCE"
                   + "[./IDENT[@text='a']]/MODIFIERS/FINAL"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);

    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRedundantModifierInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RedundantModifierCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expected = {
            "5:5: " + getCheckMessage(RedundantModifierCheck.class,
                    RedundantModifierCheck.MSG_KEY,
                    "public"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierInterface']]"
                   + "/OBJBLOCK"
                   + "/METHOD_DEF"
                   + "[./IDENT[@text='m']]",

                "/COMPILATION_UNIT/INTERFACE_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierInterface']]"
                   + "/OBJBLOCK"
                   + "/METHOD_DEF"
                   + "[./IDENT[@text='m']]"
                   + "/MODIFIERS",

                "/COMPILATION_UNIT/INTERFACE_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierInterface']]"
                   + "/OBJBLOCK"
                   + "/METHOD_DEF"
                   + "[./IDENT[@text='m']]"
                   + "/MODIFIERS"
                   + "/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void test3() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRedundantModifierWithEnum.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RedundantModifierCheck.class);
        moduleConfig.addProperty("jdkVersion", "11");

        final String[] expected = {
            "5:9: " + getCheckMessage(RedundantModifierCheck.class,
                    RedundantModifierCheck.MSG_KEY,
                    "static"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierWithEnum']]"
                   + "/OBJBLOCK/INTERFACE_DEF"
                   + "[./IDENT[@text='I']]"
                   + "/OBJBLOCK/ENUM_DEF"
                   + "[./IDENT[@text='E']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierWithEnum']]"
                   + "/OBJBLOCK/INTERFACE_DEF"
                   + "[./IDENT[@text='I']]"
                   + "/OBJBLOCK/ENUM_DEF"
                   + "[./IDENT[@text='E']]"
                   + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                   + "[./IDENT[@text='InputXpathRedundantModifierWithEnum']]"
                   + "/OBJBLOCK/INTERFACE_DEF"
                   + "[./IDENT[@text='I']]"
                   + "/OBJBLOCK/ENUM_DEF"
                   + "[./IDENT[@text='E']]"
                   + "/MODIFIERS"
                   + "/LITERAL_STATIC"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);

    }

}
