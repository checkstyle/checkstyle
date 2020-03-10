////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck;

public class XpathRegressionOneTopLevelClassTest extends AbstractXpathTestSupport {

    private final String checkName = OneTopLevelClassCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionOneTopLevelClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OneTopLevelClassCheck.class);

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(OneTopLevelClassCheck.class,
                OneTopLevelClassCheck.MSG_KEY, "ViolatingSecondClass"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='ViolatingSecondClass']]",
                "/CLASS_DEF[./IDENT[@text='ViolatingSecondClass']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='ViolatingSecondClass']]/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
