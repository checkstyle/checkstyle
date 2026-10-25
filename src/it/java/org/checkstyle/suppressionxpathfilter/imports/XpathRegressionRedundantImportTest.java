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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck;

public class XpathRegressionRedundantImportTest extends AbstractXpathTestSupport {

    private final String checkName = RedundantImportCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/imports/redundantimport";
    }

    @Test
    public void testInternal() throws Exception {
        final String[] expectedViolation = {
            "9:1: " + getCheckMessage(RedundantImportCheck.class,
                        RedundantImportCheck.MSG_SAME, "org.checkstyle.suppressionxpathfilter"
                                + ".imports.redundantimport.InputXpathRedundantImportInternal"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantImportInternal.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testLibrary() throws Exception {
        final String[] expectedViolation = {
            "9:1: " + getCheckMessage(RedundantImportCheck.class,
                        RedundantImportCheck.MSG_LANG, "java.lang.String"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantImportLibrary.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testDuplicate() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(RedundantImportCheck.class,
                        RedundantImportCheck.MSG_DUPLICATE, 9, "java.util.Scanner"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='Scanner']]");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRedundantImportDuplicate.java"),
                expectedXpathQueries, expectedViolation);
    }

}
