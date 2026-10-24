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

package org.checkstyle.suppressionxpathfilter.sizes;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck;

public class XpathRegressionOuterTypeNumberTest extends AbstractXpathTestSupport {

    private final String checkName = OuterTypeNumberCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/outertypenumber";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "8:1: " + getCheckMessage(OuterTypeNumberCheck.class,
                OuterTypeNumberCheck.MSG_KEY, 2, 1),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOuterTypeNumberDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMax() throws Exception {
        final String[] expectedViolation = {
            "8:1: " + getCheckMessage(OuterTypeNumberCheck.class,
                OuterTypeNumberCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOuterTypeNumber.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "8:1: " + getCheckMessage(OuterTypeNumberCheck.class,
                    OuterTypeNumberCheck.MSG_KEY, 3, 2),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOuterTypeNumberThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
