///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck;

public class XpathRegressionOverloadMethodsDeclarationOrderTest extends AbstractXpathTestSupport {

    private final Class<OverloadMethodsDeclarationOrderCheck> clazz =
            OverloadMethodsDeclarationOrderCheck.class;

    @Override
    protected String getCheckName() {
        return clazz.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionOverloadMethodsDeclarationOrder1.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(clazz,
                        OverloadMethodsDeclarationOrderCheck.MSG_KEY, "5"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionOverloadMethodsDeclarationOrder1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionOverloadMethodsDeclarationOrder1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionOverloadMethodsDeclarationOrder1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionOverloadMethodsDeclarationOrder2.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "30:9: " + getCheckMessage(clazz,
                    OverloadMethodsDeclarationOrderCheck.MSG_KEY, "21"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MySuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MySuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MySuppressionXpathRegressionOverloadMethodsDeclarationOrder2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
