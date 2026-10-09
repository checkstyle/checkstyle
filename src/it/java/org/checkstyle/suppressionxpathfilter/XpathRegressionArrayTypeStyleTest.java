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

package org.checkstyle.suppressionxpathfilter;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck;

public class XpathRegressionArrayTypeStyleTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ArrayTypeStyleCheck.class.getSimpleName();
    }

    @Test
    public void testVariable() throws Exception {
        final String[] expectedViolation = {
            "11:19: " + getCheckMessage(ArrayTypeStyleCheck.class, ArrayTypeStyleCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathArrayTypeStyleVariable']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='strings']]/TYPE["
                        + "./IDENT[@text='String']]/ARRAY_DECLARATOR"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathArrayTypeStyleVariable.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethodDef() throws Exception {
        final String[] expectedViolation = {
            "11:19: " + getCheckMessage(ArrayTypeStyleCheck.class, ArrayTypeStyleCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathArrayTypeStyleMethodDef']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getData']]/TYPE/ARRAY_DECLARATOR"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathArrayTypeStyleMethodDef.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testParameter() throws Exception {
        final String[] expectedViolation = {
            "11:28: " + getCheckMessage(ArrayTypeStyleCheck.class, ArrayTypeStyleCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathArrayTypeStyleParameter']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='args']]"
                    + "/TYPE[./IDENT[@text='String']]/ARRAY_DECLARATOR"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathArrayTypeStyleParameter.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
