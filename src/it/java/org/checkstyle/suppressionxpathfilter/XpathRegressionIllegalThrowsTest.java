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
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck;

public class XpathRegressionIllegalThrowsTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalThrowsCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testRuntimeException() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIllegalThrowsRuntimeException.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalThrowsCheck.class);

        final String[] expectedViolation = {
            "4:35: " + getCheckMessage(IllegalThrowsCheck.class,
                IllegalThrowsCheck.MSG_KEY, "RuntimeException"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathIllegalThrowsRuntimeException']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sayHello']]/LITERAL_THROWS"
                + "/IDENT[@text='RuntimeException']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testError() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIllegalThrowsError.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalThrowsCheck.class);

        final String[] expectedViolation = {
            "8:45: " + getCheckMessage(IllegalThrowsCheck.class,
                IllegalThrowsCheck.MSG_KEY, "java.lang.Error"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathIllegalThrowsError']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='methodTwo']]/LITERAL_THROWS"
                + "/DOT[./IDENT[@text='Error']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
