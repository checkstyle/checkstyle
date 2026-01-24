///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter.coding;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck;

public class XpathRegressionUseEnhancedSwitchTest
    extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return UseEnhancedSwitchCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/useenhancedswitch";
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathUseEnhancedSwitchSimple.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(UseEnhancedSwitchCheck.class);
        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(UseEnhancedSwitchCheck.class, UseEnhancedSwitchCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathUseEnhancedSwitchSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testExpressions() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathUseEnhancedSwitchExpressions.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(UseEnhancedSwitchCheck.class);
        final String[] expectedViolation = {
            "5:18: " + getCheckMessage(UseEnhancedSwitchCheck.class,
                UseEnhancedSwitchCheck.MSG_KEY),
        };

        final String exprXpathQuery = "/COMPILATION_UNIT"
            + "/CLASS_DEF[./IDENT[@text='InputXpathUseEnhancedSwitchExpressions']]/OBJBLOCK"
            + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT[@text='id']]/ASSIGN"
            + "/EXPR";
        final List<String> expectedXpathQueries = List.of(
            exprXpathQuery,
            exprXpathQuery + "/LITERAL_SWITCH"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
