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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoArrayTrailingCommaCheck;

public class XpathRegressionNoArrayTrailingCommaTest extends AbstractXpathTestSupport {

    private final String checkName = NoArrayTrailingCommaCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathNoArrayTrailingCommaOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoArrayTrailingCommaCheck.class);

        final String[] expectedViolation = {
            "5:37: " + getCheckMessage(NoArrayTrailingCommaCheck.class,
                NoArrayTrailingCommaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathNoArrayTrailingCommaOne']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='t1']]/ASSIGN/EXPR"
                    + "/LITERAL_NEW/ARRAY_INIT/COMMA[4]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathNoArrayTrailingCommaTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoArrayTrailingCommaCheck.class);

        final String[] expectedViolation = {
            "4:28: " + getCheckMessage(NoArrayTrailingCommaCheck.class,
                NoArrayTrailingCommaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathNoArrayTrailingCommaTwo']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='t4']]"
                    + "/ASSIGN/EXPR/LITERAL_NEW/ARRAY_INIT/COMMA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
