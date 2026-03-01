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
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineWrappingInBlockCheck;

public class XpathRegressionEmptyLineWrappingInBlockTest extends AbstractXpathTestSupport {

    private final String checkName = EmptyLineWrappingInBlockCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/emptylinewrappinginblock";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathEmptyLineWrappingInBlockOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        moduleConfig.addProperty("tokens", "CLASS_DEF");
        moduleConfig.addProperty("topSeparator", "empty_line");
        moduleConfig.addProperty("bottomSeparator", "empty_line_allowed");

        final String[] expectedViolation = {
            "3:52: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_ONE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathEmptyLineWrappingInBlockOne']]"
                        + "/OBJBLOCK",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathEmptyLineWrappingInBlockOne']]"
                        + "/OBJBLOCK/LCURLY"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathEmptyLineWrappingInBlockTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");
        moduleConfig.addProperty("topSeparator", "no_empty_line");
        moduleConfig.addProperty("bottomSeparator", "empty_line");

        final String[] expectedViolation = {
            "4:23: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_NO),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathEmptyLineWrappingInBlockTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathEmptyLineWrappingInBlockTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/LCURLY"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
