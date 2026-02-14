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

package org.checkstyle.suppressionxpathfilter.whitespace;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceBeforeEmptyBodyCheck;

public class XpathRegressionWhitespaceBeforeEmptyBodyTest extends AbstractXpathTestSupport {

    private final String checkName = WhitespaceBeforeEmptyBodyCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/whitespacebeforeemptybody";
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathWhitespaceBeforeEmptyBodyMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhitespaceBeforeEmptyBodyCheck.class);

        final String[] expectedViolation = {
            "4:18: " + getCheckMessage(WhitespaceBeforeEmptyBodyCheck.class,
                    WhitespaceBeforeEmptyBodyCheck.MSG_KEY, "method"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathWhitespaceBeforeEmptyBodyMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='method']]/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathWhitespaceBeforeEmptyBodyClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhitespaceBeforeEmptyBodyCheck.class);

        final String[] expectedViolation = {
            "4:16: " + getCheckMessage(WhitespaceBeforeEmptyBodyCheck.class,
                    WhitespaceBeforeEmptyBodyCheck.MSG_KEY, "Inner"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhitespaceBeforeEmptyBodyClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhitespaceBeforeEmptyBodyClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLoop() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathWhitespaceBeforeEmptyBodyLoop.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhitespaceBeforeEmptyBodyCheck.class);

        final String[] expectedViolation = {
            "5:21: " + getCheckMessage(WhitespaceBeforeEmptyBodyCheck.class,
                    WhitespaceBeforeEmptyBodyCheck.MSG_KEY, "while"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathWhitespaceBeforeEmptyBodyLoop']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_WHILE/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
