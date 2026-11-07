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

import com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck;

public class XpathRegressionUnusedImportsTest extends AbstractXpathTestSupport {

    private static final Class<UnusedImportsCheck> CLAZZ = UnusedImportsCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/imports/unusedimports";
    }

    @Test
    public void testUnusedImports() throws Exception {
        final String[] expectedViolation = {
            "11:8: " + getCheckMessage(CLAZZ,
                    UnusedImportsCheck.MSG_KEY, "java.util.List"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/IMPORT/DOT[./IDENT[@text='List']]/DOT/IDENT[@text='java']");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedImports.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testStatic() throws Exception {
        final String[] expectedViolation = {
            "11:15: " + getCheckMessage(CLAZZ,
                    UnusedImportsCheck.MSG_KEY, "java.util.Map.Entry"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/STATIC_IMPORT/DOT"
                        + "[./IDENT[@text='Entry']]/DOT[./IDENT[@text='Map']]"
                        + "/DOT/IDENT[@text='java']");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedImportsStatic.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testImportBreak() throws Exception {
        final String[] expectedViolation = {
            "11:8: " + getCheckMessage(CLAZZ,
                    UnusedImportsCheck.MSG_KEY, "java.util.List"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/IMPORT/DOT"
                        + "[./IDENT[@text='List']]/DOT/IDENT[@text='java']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedImportsBreak.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
