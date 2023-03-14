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
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck;

public class XpathRegressionNoWhitespaceBeforeCaseDefaultColonTest
        extends AbstractXpathTestSupport {

    private final String checkName = NoWhitespaceBeforeCaseDefaultColonCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoWhitespaceBeforeCaseDefaultColonCheck.class);

        final String[] expectedViolation = {
            "6:20: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                    NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonOne']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_CASE/COLON"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoWhitespaceBeforeCaseDefaultColonCheck.class);

        final String[] expectedViolation = {
            "10:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                    NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonTwo']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_DEFAULT/COLON"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoWhitespaceBeforeCaseDefaultColonCheck.class);

        final String[] expectedViolation = {
            "7:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                        NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonThree']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_CASE/COLON"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoWhitespaceBeforeCaseDefaultColonCheck.class);

        final String[] expectedViolation = {
            "11:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                        NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonFour']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_DEFAULT/COLON"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
