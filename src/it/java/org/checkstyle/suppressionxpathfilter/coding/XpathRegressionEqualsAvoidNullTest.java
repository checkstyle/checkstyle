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
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;

public class XpathRegressionEqualsAvoidNullTest extends AbstractXpathTestSupport {

    private static final Class<EqualsAvoidNullCheck> CLAZZ = EqualsAvoidNullCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/equalsavoidnull";
    }

    @Test
    public void testEquals() throws Exception {
        final String[] expectedViolation = {
            "13:26: " + getCheckMessage(CLAZZ,
                    EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNull']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNull']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR/METHOD_CALL");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsAvoidNull.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEqualsIgnoreCase() throws Exception {
        final String[] expectedViolation = {
            "13:36: " + getCheckMessage(CLAZZ,
                    EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullIgnoreCase']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullIgnoreCase']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR/METHOD_CALL");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsAvoidNullIgnoreCase.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testField() throws Exception {
        final String[] expectedViolation = {
            "12:25: " + getCheckMessage(CLAZZ,
                    EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullField']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullField']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/ASSIGN/EXPR/METHOD_CALL");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsAvoidNullField.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
