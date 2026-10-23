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

import com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck;

public class XpathRegressionAvoidNoArgumentSuperConstructorCallTest
        extends AbstractXpathTestSupport {

    private static final Class<AvoidNoArgumentSuperConstructorCallCheck> CLASS =
        AvoidNoArgumentSuperConstructorCallCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/avoidnoargumentsuperconstructorcall";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "11:9: " + getCheckMessage(CLASS,
                AvoidNoArgumentSuperConstructorCallCheck.MSG_CTOR),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathAvoidNoArgumentSuperConstructorCallDefault']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT["
                    + "@text='InputXpathAvoidNoArgumentSuperConstructorCallDefault']]"
                    + "/SLIST/SUPER_CTOR_CALL"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidNoArgumentSuperConstructorCallDefault.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expectedViolation = {
            "13:17: " + getCheckMessage(CLASS,
                AvoidNoArgumentSuperConstructorCallCheck.MSG_CTOR),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathAvoidNoArgumentSuperConstructorCallInnerClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='Inner']]"
                + "/SLIST/SUPER_CTOR_CALL"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidNoArgumentSuperConstructorCallInnerClass.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testStaticInnerClass() throws Exception {
        final String[] expectedViolation = {
            "12:13: " + getCheckMessage(CLASS,
                AvoidNoArgumentSuperConstructorCallCheck.MSG_CTOR),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathAvoidNoArgumentSuperConstructorCallStaticInner']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Nested']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='Nested']]"
                + "/SLIST/SUPER_CTOR_CALL"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidNoArgumentSuperConstructorCallStaticInner.java"),
                expectedXpathQueries, expectedViolation);
    }

}
