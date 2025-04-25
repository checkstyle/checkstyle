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
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.PatternVariableAssignmentCheck;
import org.junit.jupiter.api.Test;

public class XpathRegressionPatternVariableAssignmentTest extends AbstractXpathTestSupport {
    private final String checkName = PatternVariableAssignmentCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testMethod() throws Exception {

        // incomplete for now

        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathPatternVariableAssignmentMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableAssignmentCheck.class);

        final String[] expectedViolation = {
            "8:9: " + getCheckMessage(PatternVariableAssignmentCheck.class,
                    PatternVariableAssignmentCheck.MSG_KEY,
                    "s"),
        };

        final List<String> expectedXpathQueries = Arrays.asList("/COMPILATION_UNIT/CLASS_DEF["
            + "./IDENT[@text='InputXpathPatternVariableAssignmentMethod']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
            + "/SLIST/LITERAL_IF/SLIST/EXPR", "/COMPILATION_UNIT/CLASS_DEF["
            + "./IDENT[@text='InputXpathPatternVariableAssignmentMethod']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
            + "/SLIST/LITERAL_IF/SLIST/EXPR/ASSIGN[./IDENT[@text='s']]");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
