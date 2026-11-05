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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnPackageCheck.MSG_INAPPROPRIATE_TAG;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnPackageCheck;

public class XpathRegressionInappropriateJavadocBlockTagsOnPackageTest
        extends AbstractXpathTestSupport {

    private final String checkName = InappropriateJavadocBlockTagsOnPackageCheck.class
            .getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/"
                + "inappropriatejavadocblocktagsonpackage";
    }

    @Test
    public void testPackage1() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnPackageCheck.class,
                MSG_INAPPROPRIATE_TAG, "return", "package"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnPackage1.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testPackage2() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnPackageCheck.class,
                MSG_INAPPROPRIATE_TAG, "throws", "package"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnPackage2.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testPackage3() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(InappropriateJavadocBlockTagsOnPackageCheck.class,
                MSG_INAPPROPRIATE_TAG, "param", "package"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnPackage3.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
