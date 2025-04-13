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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption;

public class XpathRegressionEmptyForInitializerPadTest extends AbstractXpathTestSupport {

    private final String checkName = EmptyForInitializerPadCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testPreceded() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEmptyForInitializerPadPreceded.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(EmptyForInitializerPadCheck.class);

        final String[] expectedViolation = {
            "5:15: " + getCheckMessage(EmptyForInitializerPadCheck.class,
                EmptyForInitializerPadCheck.MSG_PRECEDED, ";"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathEmptyForInitializerPadPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/FOR_INIT",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathEmptyForInitializerPadPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/SEMI[1]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testNotPreceded() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEmptyForInitializerPadNotPreceded.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(EmptyForInitializerPadCheck.class);
        moduleConfig.addProperty("option", PadOption.SPACE.toString());

        final String[] expectedViolation = {
            "5:14: " + getCheckMessage(EmptyForInitializerPadCheck.class,
                EmptyForInitializerPadCheck.MSG_NOT_PRECEDED, ";"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathEmptyForInitializerPadNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/FOR_INIT",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathEmptyForInitializerPadNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/SEMI[1]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

}
