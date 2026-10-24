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

import static com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck.MSG_KEY_NEED_BRACES;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class XpathRegressionNeedBracesTest extends AbstractXpathTestSupport {

    private final String checkName = NeedBracesCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/blocks/needbraces";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDo() throws Exception {
        final String[] expectedViolation = {
            "22:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "do"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNeedBracesDo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_DO"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNeedBracesDo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSingleLine() throws Exception {
        final String[] expectedViolation = {
            "25:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "if"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesSingleLine']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNeedBracesSingleLine.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSingleLineLambda() throws Exception {
        final String[] expectedViolation = {
            "13:29: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "->"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesSingleLineLambda']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r3']]/ASSIGN/LAMBDA"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNeedBracesSingleLineLambda.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEmptyLoopBody() throws Exception {
        final String[] expectedViolation = {
            "18:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "while"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesEmptyLoopBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_WHILE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNeedBracesEmptyLoopBody.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
