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

package org.checkstyle.suppressionxpathfilter.javadoc;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck;

public class XpathRegressionMissingJavadocMethodTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MissingJavadocMethodCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/missingjavadocmethod";
    }

    @Test
    public void testMissingJavadocMethodCtor() throws Exception {
        final String[] expectedViolation = {
            "17:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                MissingJavadocMethodCheck.MSG_JAVADOC_MISSING,
                "InputXpathMissingJavadocMethodCtor"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathMissingJavadocMethodCtor']]"
                    + "/OBJBLOCK/CTOR_DEF[."
                    + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                    + "/OBJBLOCK/CTOR_DEF[."
                    + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                    + "/OBJBLOCK/CTOR_DEF[."
                    + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                    + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocMethodCtor.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMissingJavadocMethod() throws Exception {
        final String[] expectedViolation = {
            "17:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                MissingJavadocMethodCheck.MSG_JAVADOC_MISSING, "foo"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                    + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMissingJavadocMethodInInterface() throws Exception {
        final String[] expectedViolation = {
            "17:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                MissingJavadocMethodCheck.MSG_JAVADOC_MISSING, "foo"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethodInInterface']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]",

                "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethodInInterface']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathMissingJavadocMethodInInterface']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                    + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocMethodInInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
