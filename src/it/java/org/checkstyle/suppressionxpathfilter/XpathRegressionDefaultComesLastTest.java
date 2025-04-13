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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck;

public class XpathRegressionDefaultComesLastTest extends AbstractXpathTestSupport {

    private final String checkName = DefaultComesLastCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testNonEmptyCase() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDefaultComesLastNonEmptyCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DefaultComesLastCheck.class);

        final String[] expectedViolation = {
            "8:13: " + getCheckMessage(DefaultComesLastCheck.class,
                    DefaultComesLastCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDefaultComesLastNonEmptyCase']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/CASE_GROUP["
                + "./SLIST/EXPR/ASSIGN/IDENT[@text='id']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDefaultComesLastNonEmptyCase']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/CASE_GROUP"
                + "/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEmptyCase() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDefaultComesLastEmptyCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DefaultComesLastCheck.class);
        moduleConfig.addProperty("skipIfLastAndSharedWithCase", "true");

        final String[] expectedViolation = {
            "15:13: " + getCheckMessage(DefaultComesLastCheck.class,
                DefaultComesLastCheck.MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDefaultComesLastEmptyCase']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/CASE_GROUP"
                + "/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
