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

import com.puppycrawl.tools.checkstyle.checks.sizes.LambdaBodyLengthCheck;

public class XpathRegressionLambdaBodyLengthTest
        extends AbstractXpathTestSupport {

    private static final Class<LambdaBodyLengthCheck> CLASS =
        LambdaBodyLengthCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/lambdabodylength";
    }

    @Test
    public void testDefaultMax() throws Exception {
        final String[] expectedViolation = {
            "15:48: " + getCheckMessage(CLASS, LambdaBodyLengthCheck.MSG_KEY, 11, 10),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaBodyLengthDefaultMax']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLambdaBodyLengthDefaultMax.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testCustomMax() throws Exception {
        final String[] expectedViolation = {
            "15:25: " + getCheckMessage(CLASS, LambdaBodyLengthCheck.MSG_KEY, 6, 5),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaBodyLengthCustomMax']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/LAMBDA");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLambdaBodyLengthCustomMax.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testNestedBlockViolation() throws Exception {
        final String[] expectedViolation = {
            "13:25: " + getCheckMessage(CLASS, LambdaBodyLengthCheck.MSG_KEY, 11, 5),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaBodyLengthNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/LAMBDA");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathLambdaBodyLengthNested.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
