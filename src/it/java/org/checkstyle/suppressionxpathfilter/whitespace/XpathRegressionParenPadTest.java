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

import com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck;

public class XpathRegressionParenPadTest extends AbstractXpathTestSupport {

    private final String checkName = ParenPadCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/parenpad";
    }

    @Test
    public void testLeftFollowed() throws Exception {
        final String[] expectedViolation = {
            "17:12: " + getCheckMessage(ParenPadCheck.class,
                    ParenPadCheck.MSG_WS_FOLLOWED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParenPadLeftFollowed']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/LPAREN"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathParenPadLeftFollowed.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testLeftNotFollowed() throws Exception {
        final String[] expectedViolation = {
            "17:12: " + getCheckMessage(ParenPadCheck.class,
                    ParenPadCheck.MSG_WS_NOT_FOLLOWED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParenPadLeftNotFollowed']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/LPAREN"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathParenPadLeftNotFollowed.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testRightPreceded() throws Exception {
        final String[] expectedViolation = {
            "17:19: " + getCheckMessage(ParenPadCheck.class,
                    ParenPadCheck.MSG_WS_PRECEDED, ")"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParenPadRightPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/RPAREN"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathParenPadRightPreceded.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testRightNotPreceded() throws Exception {
        final String[] expectedViolation = {
            "17:19: " + getCheckMessage(ParenPadCheck.class,
                    ParenPadCheck.MSG_WS_NOT_PRECEDED, ")"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParenPadRightNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/RPAREN"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathParenPadRightNotPreceded.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
