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

import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;

public class XpathRegressionTypeNameTest extends AbstractXpathTestSupport {

    private final String checkName = TypeNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/typename";
    }

    @Test
    public void testDefault() throws Exception {
        final String pattern = "^[A-Z][a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "18:19: " + getCheckMessage(TypeNameCheck.class,
                        TypeNameCheck.MSG_INVALID_PATTERN, "SecondName_", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathTypeNameDefault']]"
                        + "/OBJBLOCK/CLASS_DEF/IDENT[@text='SecondName_']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeNameDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterfaceDef() throws Exception {
        final String pattern = "^I_[a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "19:15: " + getCheckMessage(TypeNameCheck.class,
                        TypeNameCheck.MSG_INVALID_PATTERN, "SecondName", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathTypeNameInterfaceDef']]"
                        + "/OBJBLOCK/INTERFACE_DEF/IDENT[@text='SecondName']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeNameInterfaceDef.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testClassDef() throws Exception {
        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final String[] expectedViolation = {
            "20:21: " + getCheckMessage(TypeNameCheck.class,
                        TypeNameCheck.MSG_INVALID_PATTERN, "ThirdName", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathTypeNameAccessModifiers']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='SecondName']]"
                        + "/OBJBLOCK/CLASS_DEF/IDENT[@text='ThirdName']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeNameAccessModifiers.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
