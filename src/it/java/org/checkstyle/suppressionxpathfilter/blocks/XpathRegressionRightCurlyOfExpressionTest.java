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

package org.checkstyle.suppressionxpathfilter.blocks;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOfExpressionCheck;

public class XpathRegressionRightCurlyOfExpressionTest extends AbstractXpathTestSupport {

    private final String checkName = RightCurlyOfExpressionCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/blocks/rightcurlyofexpression";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(
                RightCurlyOfExpressionCheck.class,
                RightCurlyOfExpressionCheck.MSG_KEY_LINE_NEW, "}", 16),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyOfExpressionOne']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='array']]/ASSIGN/EXPR"
                + "/LITERAL_NEW[./IDENT[@text='String']]"
                + "/ARRAY_INIT/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyOfExpressionOne.java"),
                expectedXpathQueries,
                expected);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expected = {
            "13:24: " + getCheckMessage(
                RightCurlyOfExpressionCheck.class,
                RightCurlyOfExpressionCheck.MSG_KEY_LINE_NEW, "}", 24),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyOfExpressionTwo']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='action']]/ASSIGN/LAMBDA/SLIST/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyOfExpressionTwo.java"),
                expectedXpathQueries,
                expected);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(
                RightCurlyOfExpressionCheck.class,
                RightCurlyOfExpressionCheck.MSG_KEY_LINE_PREVIOUS, "}", 5),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyOfExpressionThree']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='anon']]/ASSIGN/EXPR"
                + "/LITERAL_NEW[./IDENT[@text='Object']]"
                + "/OBJBLOCK/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyOfExpressionThree.java"),
                expectedXpathQueries,
                expected);
    }

}
