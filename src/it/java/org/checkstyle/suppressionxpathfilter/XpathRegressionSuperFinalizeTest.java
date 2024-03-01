///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheck;
import static com.puppycrawl.tools.checkstyle.checks.coding.AbstractSuperCheck.MSG_KEY;

public class XpathRegressionSuperFinalizeTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return SuperFinalizeCheck.class.getSimpleName();
    }

    @Test
    public void testSuperFinalizeNoSuperFinalize() throws Exception {
        final String fileName = "SuppressionXpathRegressionSuperFinalizeNoSuperFinalize.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SuperFinalizeCheck.class);

        final String[] expectedViolations = {
                "6:17: " +
                        getCheckMessage(SuperFinalizeCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT" +
                        "[@text='SuppressionXpathRegressionSuperFinalizeNoSuperFinalize']]" +
                        "/OBJBLOCK/METHOD_DEF/IDENT[@text='finalize']"
        );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolations, expectedXpathQuery);
    }

    @Test
    public void testSuperFinalizeInnerFinalize() throws Exception {
        final String fileName = "SuppressionXpathRegressionSuperFinalizeInnerFinalize.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SuperFinalizeCheck.class);

        final String[] expectedViolations = {
                "4:17: " +
                        getCheckMessage(SuperFinalizeCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT" +
                        "[@text='SuppressionXpathRegressionSuperFinalizeInnerFinalize']]" +
                        "/OBJBLOCK/METHOD_DEF/IDENT[@text='finalize']"
        );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolations, expectedXpathQuery);
    }

    @Test
    public void testSuperFinalizeOverrideClass() throws Exception {
        final String fileName = "SuppressionXpathRegressionSuperFinalizeOverrideClass.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SuperFinalizeCheck.class);

        final String[] expectedViolations = {
                "5:20: " +
                        getCheckMessage(SuperFinalizeCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT" +
                        "[@text='SuppressionXpathRegressionSuperFinalizeOverrideClass']]" +
                        "/OBJBLOCK/METHOD_DEF/IDENT[@text='finalize']"
        );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolations, expectedXpathQuery);
    }

    @Test
    public void testSuperFinalizeMethodReference() throws Exception {
        final String fileName = "SuppressionXpathRegressionSuperFinalizeMethodReference.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SuperFinalizeCheck.class);

        final String[] expectedViolations = {
                "17:20:" +
                        getCheckMessage(SuperFinalizeCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='ClassWithFinalizer']]" +
                        "/OBJBLOCK/METHOD_DEF/IDENT[@text='finalize']"
        );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolations, expectedXpathQuery);
    }
}
