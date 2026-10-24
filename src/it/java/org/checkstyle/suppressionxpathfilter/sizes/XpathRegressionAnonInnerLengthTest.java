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

package org.checkstyle.suppressionxpathfilter.sizes;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheck;

public class XpathRegressionAnonInnerLengthTest extends AbstractXpathTestSupport {

    private final String checkName = AnonInnerLengthCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/anoninnerlength";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "15:29: " + getCheckMessage(AnonInnerLengthCheck.class,
                AnonInnerLengthCheck.MSG_KEY, 26, 20),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnonInnerLengthDefault']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='runnable']]"
                + "/ASSIGN/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnonInnerLengthDefault']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='runnable']]"
                + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Runnable']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnonInnerLengthDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMaxLength() throws Exception {
        final int maxLen = 5;
        final String[] expectedViolation = {
            "15:35: " + getCheckMessage(AnonInnerLengthCheck.class,
                    AnonInnerLengthCheck.MSG_KEY, 6, maxLen),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnonInnerLength']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='compare']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='comp']]/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnonInnerLength']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='compare']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='comp']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='Comparator']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnonInnerLength.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testField() throws Exception {
        final int maxLen = 5;
        final String[] expectedViolation = {
            "12:25: " + getCheckMessage(AnonInnerLengthCheck.class,
                    AnonInnerLengthCheck.MSG_KEY, 9, maxLen),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnonInnerLengthField']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='runnable']]"
                        + "/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnonInnerLengthField']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='runnable']]"
                        + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Runnable']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnonInnerLengthField.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
