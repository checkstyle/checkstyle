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
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedCatchParameterShouldBeUnnamedCheck;

public class XpathRegressionUnusedCatchParameterShouldBeUnnamedTest
        extends AbstractXpathTestSupport {

    private final String checkName = UnusedCatchParameterShouldBeUnnamedCheck.class
                                                .getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathUnusedCatchParameterShouldBeUnnamedSimple.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedCatchParameterShouldBeUnnamedCheck.class);

        final String[] expectedViolation = {
            "10:16: " + getCheckMessage(UnusedCatchParameterShouldBeUnnamedCheck.class,
                    UnusedCatchParameterShouldBeUnnamedCheck.MSG_UNUSED_CATCH_PARAMETER,
                    "e"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathUnusedCatchParameterShouldBeUnnamedSimple']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY/"
                        + "LITERAL_CATCH/PARAMETER_DEF[./IDENT[@text='e']]",
                "/COMPILATION_UNIT/CLASS_DEF["
                        + "./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedSimple']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                        + "LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF[./IDENT[@text='e']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF["
                        + "./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedSimple']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY/"
                        + "LITERAL_CATCH/PARAMETER_DEF[./IDENT[@text='e']]"
                        + "/TYPE[./IDENT[@text='Exception']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedSimple']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY"
                        + "/LITERAL_CATCH/PARAMETER_DEF[./IDENT[@text='e']]"
                        + "/TYPE/IDENT[@text='Exception']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNested() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathUnusedCatchParameterShouldBeUnnamedNested.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedCatchParameterShouldBeUnnamedCheck.class);

        final String[] expectedViolation = {
            "14:20: " + getCheckMessage(UnusedCatchParameterShouldBeUnnamedCheck.class,
                    UnusedCatchParameterShouldBeUnnamedCheck.MSG_UNUSED_CATCH_PARAMETER,
                    "exception"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF["
                        + "./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedNested']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY/"
                        + "LITERAL_CATCH/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF"
                        + "[./IDENT[@text='exception']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathUnusedCatchParameterShouldBeUnnamedNested']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY"
                        + "/LITERAL_CATCH/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF["
                        + "./IDENT[@text='exception']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedNested']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY/"
                        + "LITERAL_CATCH/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF"
                        + "[./IDENT[@text='exception']]/TYPE[./IDENT[@text='Exception']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathUnusedCatchParameterShouldBeUnnamedNested']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY"
                        + "/LITERAL_CATCH/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF"
                        + "[./IDENT[@text='exception']]/TYPE/IDENT[@text='Exception']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
