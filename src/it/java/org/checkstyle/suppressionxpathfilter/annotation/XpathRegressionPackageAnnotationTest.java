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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.annotation.PackageAnnotationCheck;

public class XpathRegressionPackageAnnotationTest extends AbstractXpathTestSupport {

    private final String checkName = PackageAnnotationCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/packageannotation";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(PackageAnnotationCheck.class,
                PackageAnnotationCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries =
                List.of("/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getNonCompilablePath("InputXpathPackageAnnotationOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(PackageAnnotationCheck.class,
                PackageAnnotationCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries =
                List.of("/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getNonCompilablePath("InputXpathPackageAnnotationTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "11:1: " + getCheckMessage(PackageAnnotationCheck.class,
                PackageAnnotationCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries =
                List.of("/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF");

        verifyXpathWithInlineConfigParser(
                getNonCompilablePath("InputXpathPackageAnnotationThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
