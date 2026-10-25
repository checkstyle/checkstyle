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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck;

public class XpathRegressionOperatorWrapTest extends AbstractXpathTestSupport {

    private final String checkName = OperatorWrapCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/operatorwrap";
    }

    @Test
    public void testOperatorWrapNewLine() throws Exception {
        final String[] expectedViolation = {
            "16:19: " + getCheckMessage(OperatorWrapCheck.class,
                        OperatorWrapCheck.MSG_LINE_NEW, "+"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathOperatorWrapNewLine']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='x']]"
                        + "/ASSIGN/EXPR/MINUS[./NUM_INT[@text='4']]"
                        + "/MINUS[./NUM_INT[@text='3']]"
                        + "/PLUS[./NUM_INT[@text='1']]"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOperatorWrapNewLine.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testOperatorWrapPreviousLine() throws Exception {
        final String[] expectedViolation = {
            "13:11: " + getCheckMessage(OperatorWrapCheck.class,
                        OperatorWrapCheck.MSG_LINE_PREVIOUS, "="),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
             "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathOperatorWrapPreviousLine']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]"
                + "/ASSIGN"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOperatorWrapPreviousLine.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testOperatorWrapInnerClass() throws Exception {
        final String[] expectedViolation = {
            "17:23: " + getCheckMessage(OperatorWrapCheck.class,
                        OperatorWrapCheck.MSG_LINE_NEW, "+"),
        };

        final List<String> expectedXpathQueries = List.of(
             "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathOperatorWrapInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='compute']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='y']]"
                + "/ASSIGN/EXPR",
             "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathOperatorWrapInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='compute']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='y']]"
                + "/ASSIGN/EXPR/PLUS[./NUM_INT[@text='5']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOperatorWrapInnerClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
