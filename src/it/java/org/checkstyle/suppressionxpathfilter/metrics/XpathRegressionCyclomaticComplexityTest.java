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
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck;

public class XpathRegressionCyclomaticComplexityTest extends AbstractXpathTestSupport {

    private final String checkName = CyclomaticComplexityCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/metrics/cyclomaticcomplexity";
    }

    @Test
    public void testConditionals() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 2, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
                );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCyclomaticComplexityConditionals.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSwitchBlock() throws Exception {
        final String[] expectedViolation = {
            "17:5: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 5, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathCyclomaticComplexitySwitchBlock']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo2']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathCyclomaticComplexitySwitchBlock']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo2']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathCyclomaticComplexitySwitchBlock']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo2']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCyclomaticComplexitySwitchBlock.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInnerClassLoops() throws Exception {
        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityInnerClassLoops']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='loops']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityInnerClassLoops']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='loops']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCyclomaticComplexityInnerClassLoops']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='loops']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCyclomaticComplexityInnerClassLoops.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
