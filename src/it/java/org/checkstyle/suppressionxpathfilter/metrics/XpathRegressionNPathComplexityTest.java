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

package org.checkstyle.suppressionxpathfilter.metrics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck;

// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XpathRegressionNPathComplexityTest extends AbstractXpathTestSupport {

    private final String checkName = NPathComplexityCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/metrics/npathcomplexity";
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(NPathComplexityCheck.class,
                NPathComplexityCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNPathComplexityMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testStaticBlock() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(NPathComplexityCheck.class,
                NPathComplexityCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityStaticBlock']]"
                + "/OBJBLOCK/STATIC_INIT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNPathComplexityStaticBlock.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testConstructor() throws Exception {
        final String[] expectedViolation = {
            "12:5: " + getCheckMessage(NPathComplexityCheck.class,
                NPathComplexityCheck.MSG_KEY, 2, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityConstructor']]/OBJBLOCK"
                + "/CTOR_DEF[./IDENT[@text='InputXpathNPathComplexityConstructor']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityConstructor']]/OBJBLOCK"
                + "/CTOR_DEF[./IDENT[@text='InputXpathNPathComplexityConstructor']]"
                + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNPathComplexityConstructor']]/OBJBLOCK"
                + "/CTOR_DEF[./IDENT[@text='InputXpathNPathComplexityConstructor']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNPathComplexityConstructor.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
