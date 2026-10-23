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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck;

public class XpathRegressionArrayTrailingCommaTest extends AbstractXpathTestSupport {

    private final String checkName = ArrayTrailingCommaCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/arraytrailingcomma";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "23:9: " + getCheckMessage(ArrayTrailingCommaCheck.class,
                ArrayTrailingCommaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathArrayTrailingCommaLinear']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a2']]/ASSIGN/EXPR/LITERAL_NEW"
                + "/ARRAY_INIT/EXPR[./NUM_INT[@text='3']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathArrayTrailingCommaLinear']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a2']]/ASSIGN/EXPR/LITERAL_NEW"
                + "/ARRAY_INIT/EXPR/NUM_INT[@text='3']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathArrayTrailingCommaLinear.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "24:9: " + getCheckMessage(ArrayTrailingCommaCheck.class,
                ArrayTrailingCommaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathArrayTrailingCommaMatrix']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='d2']]/ASSIGN/EXPR/LITERAL_NEW"
                + "/ARRAY_INIT/ARRAY_INIT[./EXPR/NUM_INT[@text='5']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathArrayTrailingCommaMatrix.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testInsideMethod() throws Exception {
        final String[] expectedViolation = {
            "16:17: " + getCheckMessage(ArrayTrailingCommaCheck.class,
                ArrayTrailingCommaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathArrayTrailingCommaInsideMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='a']]/ASSIGN/EXPR/LITERAL_NEW"
                + "/ARRAY_INIT/EXPR[./NUM_INT[@text='3']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathArrayTrailingCommaInsideMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='a']]/ASSIGN/EXPR/LITERAL_NEW"
                + "/ARRAY_INIT/EXPR/NUM_INT[@text='3']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathArrayTrailingCommaInsideMethod.java"),
            expectedXpathQueries,
            expectedViolation);
    }

}
