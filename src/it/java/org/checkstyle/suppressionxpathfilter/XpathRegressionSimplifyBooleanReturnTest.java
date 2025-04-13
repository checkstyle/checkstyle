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

import static com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanReturnCheck.MSG_KEY;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanReturnCheck;

public class XpathRegressionSimplifyBooleanReturnTest extends AbstractXpathTestSupport {

    private static final Class<SimplifyBooleanReturnCheck> CLASS = SimplifyBooleanReturnCheck.class;
    private final String checkName = CLASS.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testIfBooleanEqualsBoolean() throws Exception {
        final File fileToProcess = new File(
            getPath(
                "InputXpathSimplifyBooleanReturnIfBooleanEqualsBoolean.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(CLASS, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathSimplifyBooleanReturnIfBooleanEqualsBoolean']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='toTest']]/SLIST/LITERAL_IF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testIfBooleanReturnBoolean() throws Exception {
        final File fileToProcess = new File(
            getPath(
                "InputXpathSimplifyBooleanReturnIfBooleanReturnBoolean.java"
            ));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "11:13: " + getCheckMessage(CLASS, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathSimplifyBooleanReturnIfBooleanReturnBoolean']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='toTest']]/SLIST/EXPR/METHOD_CALL/ELIST"
                + "/LAMBDA[./IDENT[@text='statement']]/SLIST/LITERAL_IF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
