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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck;

public class XpathRegressionRedundantThisTest extends AbstractXpathTestSupport {

    private final String checkName = RedundantThisCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/redundantthis";
    }

    @Test
    public void testSimple() throws Exception {
        final String[] expectedViolation = {
            "14:9: " + getCheckMessage(RedundantThisCheck.class,
                RedundantThisCheck.MSG_KEY_FIELD, "name"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRedundantThisSimple']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='setName']]/SLIST/EXPR/ASSIGN"
                + "[./IDENT[@text='value']]/DOT[./IDENT[@text='name']]"
                + "/LITERAL_THIS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantThisSimple.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInner() throws Exception {
        final String[] expectedViolation = {
            "15:20: " + getCheckMessage(RedundantThisCheck.class,
                    RedundantThisCheck.MSG_KEY_FIELD, "name"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathRedundantThisInner']]/OBJBLOCK"
                        + "/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='getName']]/SLIST/LITERAL_RETURN/EXPR/DOT"
                        + "[./IDENT[@text='name']]/LITERAL_THIS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantThisInner.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethodCall() throws Exception {
        final String[] expectedViolation = {
            "18:9: " + getCheckMessage(RedundantThisCheck.class,
                    RedundantThisCheck.MSG_KEY_METHOD, "validate"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathRedundantThisMethodCall']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='process']]/SLIST/EXPR/METHOD_CALL"
                        + "/DOT[./IDENT[@text='validate']]/LITERAL_THIS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantThisMethodCall.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
