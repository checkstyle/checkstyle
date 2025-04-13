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
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck;

public class XpathRegressionUnnecessarySemicolonAfterOuterTypeDeclarationTest
    extends AbstractXpathTestSupport {

    private static final Class<UnnecessarySemicolonAfterOuterTypeDeclarationCheck> CLASS =
        UnnecessarySemicolonAfterOuterTypeDeclarationCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathUnnecessarySemicolonAfterOuterTypeDeclarationSimple.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "5:2: " + getCheckMessage(CLASS,
                UnnecessarySemicolonAfterOuterTypeDeclarationCheck.MSG_SEMI),
        };

        final List<String> expectedXpathQueries =
            Collections.singletonList("/COMPILATION_UNIT/SEMI");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInnerTypes() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathUnnecessarySemicolonAfterOuterTypeDeclarationInnerTypes"
                + ".java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "21:2: " + getCheckMessage(CLASS,
                UnnecessarySemicolonAfterOuterTypeDeclarationCheck.MSG_SEMI),
        };

        final List<String> expectedXpathQueries =
            Collections.singletonList("/COMPILATION_UNIT/SEMI");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
