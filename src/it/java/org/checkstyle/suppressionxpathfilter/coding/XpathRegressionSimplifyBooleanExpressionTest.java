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

import static com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck.MSG_KEY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck;

public class XpathRegressionSimplifyBooleanExpressionTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return SimplifyBooleanExpressionCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/simplifybooleanexpression";
    }

    @Test
    public void testSimple() throws Exception {
        final String[] expectedViolations = {
            "14:13: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR",
                 "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR/LNOT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSimplifyBooleanExpressionSimple.java"),
                expectedXpathQuery,
                expectedViolations);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String[] expectedViolations = {
            "14:19: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR/EQUAL[./IDENT[@text='a']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSimplifyBooleanExpressionAnonymous.java"),
                expectedXpathQuery,
                expectedViolations);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolations = {
            "13:20: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionInterface']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF[./IDENT"
                + "[@text='test']]/SLIST/LITERAL_IF/EXPR/LNOT/NOT_EQUAL[./IDENT[@text='b']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSimplifyBooleanExpressionInterface.java"),
                expectedXpathQuery,
                expectedViolations);
    }

}
