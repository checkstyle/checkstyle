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

import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;

public class XpathRegressionMissingJavadocTypeTest extends AbstractXpathTestSupport {

    private final String checkName = MissingJavadocTypeCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/missingjavadoctype";
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "14:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "InputXpathMissingJavadocTypeClass"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocTypeClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testScope() throws Exception {
        final String[] expectedViolation = {
            "18:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "Test"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocTypeScope.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testExcluded() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "Test"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocTypeExcluded.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expectedViolation = {
            "17:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "innerClass"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]/AT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocTypeAnnotation.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testToken() throws Exception {
        final String[] expectedViolation = {
            "14:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "InputXpathMissingJavadocTypeToken"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingJavadocTypeToken.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
