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
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;

public class XpathRegressionMagicNumberTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MagicNumberCheck.class.getSimpleName();
    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMagicNumberVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MagicNumberCheck.class);

        final String[] expectedViolation = {
            "5:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "5"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMagicNumberVariable']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='d']]"
                    + "/ASSIGN/EXPR[./NUM_INT[@text='5']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMagicNumberVariable']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='d']]"
                    + "/ASSIGN/EXPR/NUM_INT[@text='5']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testMethodDef() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMagicNumberMethodDef.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MagicNumberCheck.class);

        final String[] expectedViolation = {
            "5:17: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "20"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMagicNumberMethodDef']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodWithMagicNumber']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR[./"
                        + "NUM_INT[@text='20']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMagicNumberMethodDef']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodWithMagicNumber']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR/NU"
                        + "M_INT[@text='20']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAnotherVariable() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMagicNumberAnotherVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MagicNumberCheck.class);

        final String[] expectedViolation = {
            "13:21: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "20"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMagicNumberAnotherVariable']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='performOperation']]"
                        + "/SLIST/LITERAL_TRY/LITERAL_CATCH/SLIST/LITERAL_IF"
                        + "/LITERAL_ELSE/SLIST/EXPR/ASSIGN"
                        + "[./IDENT[@text='a']]/NUM_INT[@text='20']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
