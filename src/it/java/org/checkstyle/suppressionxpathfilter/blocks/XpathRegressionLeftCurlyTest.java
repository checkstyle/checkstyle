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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;

public class XpathRegressionLeftCurlyTest extends AbstractXpathTestSupport {

    private final String checkName = LeftCurlyCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/blocks/leftcurly";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "17:1: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS, "{", 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathLeftCurlyOne']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathLeftCurlyOne']]/OBJBLOCK/LCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLeftCurlyOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "16:37: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 37),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathLeftCurlyTwo']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathLeftCurlyTwo']]/OBJBLOCK/LCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLeftCurlyTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "18:19: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_BREAK_AFTER, "{", 19),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLeftCurlyThree']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLeftCurlyThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
