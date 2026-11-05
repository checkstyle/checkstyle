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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnTypeCheck.MSG_INAPPROPRIATE_TAG;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnTypeCheck;

public class XpathRegressionInappropriateJavadocBlockTagsOnTypeTest
        extends AbstractXpathTestSupport {

    private final String checkName = InappropriateJavadocBlockTagsOnTypeCheck.class
            .getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/"
                + "inappropriatejavadocblocktagsontype";
    }

    @Test
    public void testInappropriateClassTag() throws Exception {
        final String[] expectedViolation = {
            "14:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnTypeCheck.class,
                MSG_INAPPROPRIATE_TAG, "return",
                "InputXpathInappropriateJavadocBlockTagsOnTypeClass"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeClass']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeClass']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeClass']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnTypeClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInappropriateInterfaceTag() throws Exception {
        final String[] expectedViolation = {
            "14:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnTypeCheck.class,
                MSG_INAPPROPRIATE_TAG, "return",
                "InputXpathInappropriateJavadocBlockTagsOnTypeInterface"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeInterface']]",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeInterface']]/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeInterface']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnTypeInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInappropriateRecordTag() throws Exception {
        final String[] expectedViolation = {
            "14:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnTypeCheck.class,
                MSG_INAPPROPRIATE_TAG, "return",
                "InputXpathInappropriateJavadocBlockTagsOnTypeRecord"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeRecord']]",
                "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeRecord']]/MODIFIERS",
                "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='"
                        + "InputXpathInappropriateJavadocBlockTagsOnTypeRecord']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnTypeRecord.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
