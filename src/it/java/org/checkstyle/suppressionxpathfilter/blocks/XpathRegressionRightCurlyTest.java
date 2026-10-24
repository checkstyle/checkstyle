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

import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;

public class XpathRegressionRightCurlyTest extends AbstractXpathTestSupport {

    private final String checkName = RightCurlyCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/blocks/rightcurly";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_SAME, "}", 9),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "17:15: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_ALONE, "}", 15),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyTwo']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='fooMethod']]/SLIST/LITERAL_TRY/SLIST/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "13:72: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_ALONE, "}", 72),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyThree']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFour() throws Exception {
        final String[] expectedViolation = {
            "15:27: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyFour']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRightCurlyFour.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
