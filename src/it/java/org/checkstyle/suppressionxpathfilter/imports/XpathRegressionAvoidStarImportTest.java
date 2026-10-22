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

package org.checkstyle.suppressionxpathfilter.imports;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck;

public class XpathRegressionAvoidStarImportTest
        extends AbstractXpathTestSupport {

    private static final Class<AvoidStarImportCheck> CLASS =
        AvoidStarImportCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/imports/avoidstarimport";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "14:42: " + getCheckMessage(CLASS,
                AvoidStarImportCheck.MSG_KEY, "javax.swing.WindowConstants.*"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/STATIC_IMPORT/DOT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidStarImportOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "15:15: " + getCheckMessage(CLASS,
                AvoidStarImportCheck.MSG_KEY, "java.io.*"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/IMPORT/DOT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidStarImportTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "14:17: " + getCheckMessage(CLASS,
                AvoidStarImportCheck.MSG_KEY, "java.util.*"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/IMPORT/DOT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidStarImportThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
