///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck;

public class XpathRegressionAvoidStaticImportTest
        extends AbstractXpathTestSupport {

    private static final Class<AvoidStaticImportCheck> CLASS =
        AvoidStaticImportCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathAvoidStaticImportOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "3:42: " + getCheckMessage(CLASS,
                AvoidStaticImportCheck.MSG_KEY, "javax.swing.WindowConstants.*"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT/DOT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathAvoidStaticImportTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "3:27: " + getCheckMessage(CLASS,
                AvoidStaticImportCheck.MSG_KEY, "java.io.File.createTempFile"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT/DOT[./IDENT[@text='createTempFile']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
