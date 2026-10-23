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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

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
        final String[] expectedViolation = {
            "13:52: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
        };

        final String baseXpathOne = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockOne']]";
        final List<String> expectedXpathQueries = Arrays.asList(
                baseXpathOne + "/OBJBLOCK",
                baseXpathOne + "/OBJBLOCK/LCURLY"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyLineWrappingInBlockOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "13:21: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_NO, "{"),
        };

        final String baseXpathTwo = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockTwo']]";
        final List<String> expectedXpathQueries = Collections.singletonList(
                baseXpathTwo + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyLineWrappingInBlockTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                    EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO, "}"),
        };

        final String baseXpathThree = "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                + "InputXpathEmptyLineWrappingInBlockThree']]";
        final List<String> expectedXpathQueries = Collections.singletonList(
                baseXpathThree + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]/SLIST/RCURLY"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyLineWrappingInBlockThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
