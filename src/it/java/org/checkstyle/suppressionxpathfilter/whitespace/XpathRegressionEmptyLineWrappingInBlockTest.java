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

        final String baseXpathOne = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockOne']]";
        final List<String> expectedXpathQueries = Arrays.asList(
                baseXpathOne + "/OBJBLOCK",
                baseXpathOne + "/OBJBLOCK/LCURLY"
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
            "4:21: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_NO),
        };

        final String baseXpathTwo = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockTwo']]";
        final List<String> expectedXpathQueries = Arrays.asList(
                baseXpathTwo + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathEmptyLineWrappingInBlockThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");
        moduleConfig.addProperty("topSeparator", "empty_line_allowed");
        moduleConfig.addProperty("bottomSeparator", "no_empty_line");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO),
        };

        final String baseXpathThree = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockThree']]";
        final List<String> expectedXpathQueries = Arrays.asList(
                baseXpathThree + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]/SLIST/RCURLY"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
