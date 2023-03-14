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
import com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck;

public class XpathRegressionUnusedImportsTest extends AbstractXpathTestSupport {

    private final String checkName = UnusedImportsCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionUnusedImportsOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedImportsCheck.class);

        final String[] expectedViolation = {
            "3:8: " + getCheckMessage(UnusedImportsCheck.class,
                    UnusedImportsCheck.MSG_KEY, "java.util.List"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT/DOT[./IDENT[@text='List']]/DOT/IDENT[@text='java']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionUnusedImportsTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedImportsCheck.class);

        final String[] expectedViolation = {
            "3:15: " + getCheckMessage(UnusedImportsCheck.class,
                    UnusedImportsCheck.MSG_KEY, "java.util.Map.Entry"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT/DOT"
                        + "[./IDENT[@text='Entry']]/DOT[./IDENT[@text='Map']]"
                        + "/DOT/IDENT[@text='java']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
