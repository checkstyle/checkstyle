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

package org.checkstyle.suppressionxpathfilter.design;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck;

public class XpathRegressionMutableExceptionTest extends AbstractXpathTestSupport {

    private final String checkName = MutableExceptionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/design/mutableexception";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "14:9: " + getCheckMessage(MutableExceptionCheck.class,
                MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS/LITERAL_PRIVATE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMutableExceptionDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testClassName() throws Exception {
        final String[] expectedViolation = {
            "13:3: " + getCheckMessage(MutableExceptionCheck.class,
                    MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMutableExceptionClassName.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testExtendedClassName() throws Exception {
        final String[] expectedViolation = {
            "15:9: " + getCheckMessage(MutableExceptionCheck.class,
                    MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS/LITERAL_PRIVATE");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMutableExceptionExtendedClassName.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
