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

import static com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck.MSG_KEY;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck;

public class XpathRegressionPackageNameTest extends AbstractXpathTestSupport {

    private final String checkName = PackageNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/packagename";
    }

    @Test
    public void testOne() throws Exception {
        final String pattern = "[A-Z]+";

        final String[] expectedViolation = {
            "9:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                    "org.checkstyle.suppressionxpathfilter.naming.packagename",
                    pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/PACKAGE_DEF/DOT"
                        + "[./IDENT[@text='packagename']]/DOT"
                        + "[./IDENT[@text='naming']]/DOT"
                        + "[./IDENT[@text='suppressionxpathfilter']]"
                        + "/DOT/IDENT[@text='org']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPackageNameOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String pattern = "^[a-z]+(\\.[a-z][a-z0-9]*)*$";

        final String[] expectedViolations = {
            "10:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                    "org.checkstyle.suppressionxpathfilter.PACKAGENAME",
                    pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT"
                        + "[@text='PACKAGENAME']]/DOT[./IDENT"
                        + "[@text='suppressionxpathfilter']]"
                        + "/DOT/IDENT[@text='org']"
        );

        verifyXpathWithInlineConfigParser(
                getNonCompilablePath("InputXpathPackageNameThree.java"),
                expectedXpathQueries,
                expectedViolations);
    }

    @Test
    public void testTwo() throws Exception {
        final String pattern = "[A-Z]+";

        final String[] expectedViolation = {
            "10:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                    "org.checkstyle.suppressionxpathfilter.naming.packagename",
                    pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/PACKAGE_DEF/DOT"
                        + "[./IDENT[@text='packagename']]/DOT"
                        + "[./IDENT[@text='naming']]/DOT"
                        + "[./IDENT[@text='suppressionxpathfilter']]"
                        + "/DOT/IDENT[@text='org']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPackageNameTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
