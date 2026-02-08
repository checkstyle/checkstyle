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
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedPrivateFieldCheck;

public class XpathRegressionUnusedPrivateFieldTest extends AbstractXpathTestSupport {
    private final String checkName = UnusedPrivateFieldCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/unusedprivatefield";
    }

    @Test
    public void testPrivateField() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathUnusedPrivateFieldOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedPrivateFieldCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(UnusedPrivateFieldCheck.class,
                    UnusedPrivateFieldCheck.MSG_PRIVATE_FIELD, "unused"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                      + "'InputXpathUnusedPrivateFieldOne']]/"
                      + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unused']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                      + "'InputXpathUnusedPrivateFieldOne']]/"
                      + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unused']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                       + "InputXpathUnusedPrivateFieldOne']]/"
                       + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unused']]/MODIFIERS/LITERAL_PRIVATE"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFullCoverage() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathUnusedPrivateFieldTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedPrivateFieldCheck.class);
        moduleConfig.addProperty("checkUnusedPrivateField", "true");

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(UnusedPrivateFieldCheck.class,
                    UnusedPrivateFieldCheck.MSG_PRIVATE_FIELD, "unusedPrivate"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                      + "'InputXpathUnusedPrivateFieldTwo']]/"
                      + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unusedPrivate']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                       + "'InputXpathUnusedPrivateFieldTwo']]/"
                       + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unusedPrivate']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                       + "'InputXpathUnusedPrivateFieldTwo']]/"
                       + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unusedPrivate']]/"
                       + "MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
