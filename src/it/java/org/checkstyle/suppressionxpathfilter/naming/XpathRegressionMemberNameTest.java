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

package org.checkstyle.suppressionxpathfilter.naming;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class XpathRegressionMemberNameTest extends AbstractXpathTestSupport {

    private final String checkName = MemberNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/membername";
    }

    @Test
    public void testDefault() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "16:17: " + getCheckMessage(MemberNameCheck.class,
                        MemberNameCheck.MSG_INVALID_PATTERN, "NUM2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathMemberNameDefault']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM2']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMemberNameDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testIgnoreProtected() throws Exception {
        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "17:20: " + getCheckMessage(MemberNameCheck.class,
                        MemberNameCheck.MSG_INVALID_PATTERN, "NUM1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathMemberNameIgnoreProtected']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM1']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMemberNameIgnoreProtected.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMemberNameEnum() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "16:16: " + getCheckMessage(MemberNameCheck.class,
                        MemberNameCheck.MSG_INVALID_PATTERN, "NUM3", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/ENUM_DEF[./IDENT[@text='InputXpathMemberNameEnum']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM3']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMemberNameEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
