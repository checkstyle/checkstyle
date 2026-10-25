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

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck;

public class XpathRegressionClassTypeParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = ClassTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/classtypeparametername";
    }

    @Test
    public void testClass() throws Exception {
        final String pattern = "^[A-Z]$";

        final String[] expectedViolation = {
            "12:20: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        ClassTypeParameterNameCheck.MSG_INVALID_PATTERN, "abc", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='abc']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER/IDENT[@text='abc']"
        );

        verifyXpathWithInlineConfigParser(getPath("InputXpathClassTypeParameterNameClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String pattern = "^[A-Z]{2,}$";

        final String[] expectedViolation = {
            "16:20: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        ClassTypeParameterNameCheck.MSG_INVALID_PATTERN, "aBc", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameInterface']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='aBc']]",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameInterface']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='aBc']"
        );

        verifyXpathWithInlineConfigParser(getPath("InputXpathClassTypeParameterNameInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String pattern = "(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)";

        final String[] expectedViolation = {
            "20:24: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        ClassTypeParameterNameCheck.MSG_INVALID_PATTERN, "ABC", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest2']]/SLIST/CLASS_DEF"
                        + "[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER[./IDENT[@text='ABC']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest2']]/SLIST/CLASS_DEF"
                        + "[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER/IDENT[@text='ABC']"
        );

        verifyXpathWithInlineConfigParser(getPath("InputXpathClassTypeParameterNameMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
