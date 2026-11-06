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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;

public class XpathRegressionImportControlTest extends AbstractXpathTestSupport {

    private final String checkName = ImportControlCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/imports/importcontrol";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "11:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_DISALLOWED, "java.util.Scanner"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/IMPORT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathImportControlOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_UNKNOWN_PKG),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathImportControlTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_MISSING_FILE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathImportControlThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFour() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_DISALLOWED, "java.util.Scanner"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='Scanner']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathImportControlFour.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
