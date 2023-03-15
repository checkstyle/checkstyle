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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck;

public class XpathRegressionArrayTypeStyleTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ArrayTypeStyleCheck.class.getSimpleName();
    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionArrayTypeStyleVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayTypeStyleCheck.class);

        final String[] expectedViolation = {
            "4:19: " + getCheckMessage(ArrayTypeStyleCheck.class, ArrayTypeStyleCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionArrayTypeStyleVariable']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='strings']]/TYPE["
                        + "./IDENT[@text='String']]/ARRAY_DECLARATOR"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethodDef() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionArrayTypeStyleMethodDef.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayTypeStyleCheck.class);

        final String[] expectedViolation = {
            "4:19: " + getCheckMessage(ArrayTypeStyleCheck.class, ArrayTypeStyleCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionArrayTypeStyleMethodDef']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getData']]/TYPE/ARRAY_DECLARATOR"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
