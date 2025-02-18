///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck;

public class XpathRegressionDescendantTokenTest extends AbstractXpathTestSupport {

    private final String checkName = DescendantTokenCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSwitchNoDefault() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDescendantTokenSwitchNoDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DescendantTokenCheck.class);
        moduleConfig.addProperty("tokens", "LITERAL_SWITCH");
        moduleConfig.addProperty("maximumDepth", "2");
        moduleConfig.addProperty("limitedTokens", "LITERAL_DEFAULT");
        moduleConfig.addProperty("minimumNumber", "1");

        final String[] expected = {
            "7:9: " + getCheckMessage(DescendantTokenCheck.class,
                    DescendantTokenCheck.MSG_KEY_MIN,
                    0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathDescendantTokenSwitchNoDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT"
                    + "[@text='testMethod1']]/SLIST/LITERAL_SWITCH"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testSwitchTooManyCases() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDescendantTokenSwitchTooManyCases.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DescendantTokenCheck.class);
        moduleConfig.addProperty("tokens", "LITERAL_SWITCH");
        moduleConfig.addProperty("limitedTokens", "LITERAL_CASE");
        moduleConfig.addProperty("maximumDepth", "2");
        moduleConfig.addProperty("maximumNumber", "1");

        final String[] expected = {
            "8:13: " + getCheckMessage(DescendantTokenCheck.class,
                    DescendantTokenCheck.MSG_KEY_MAX,
                    2, 1, "LITERAL_SWITCH", "LITERAL_CASE"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDescendantTokenSwitchTooManyCases']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT"
                        + "[@text='testMethod1']]/SLIST/VARIABLE_DEF[./IDENT[@text='switchLogic']]"
                        + "/ASSIGN/LAMBDA/SLIST/LITERAL_SWITCH"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testNestedSwitch() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDescendantTokenNestedSwitch.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DescendantTokenCheck.class);

        moduleConfig.addProperty("tokens", "CASE_GROUP");
        moduleConfig.addProperty("limitedTokens", "LITERAL_SWITCH");
        moduleConfig.addProperty("maximumNumber", "0");

        final String[] expected = {
            "12:13: " + getCheckMessage(DescendantTokenCheck.class,
                    DescendantTokenCheck.MSG_KEY_MAX,
                    1, 0, "CASE_GROUP", "LITERAL_SWITCH"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathDescendantTokenNestedSwitch']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='testMethod1']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP[./LITERAL_CASE/EXPR/NUM_INT[@text='2']]",
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathDescendantTokenNestedSwitch']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='testMethod1']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP/LITERAL_CASE"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }
}

