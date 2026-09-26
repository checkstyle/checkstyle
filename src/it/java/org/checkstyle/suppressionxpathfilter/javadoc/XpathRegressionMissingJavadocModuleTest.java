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

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocModuleCheck;

public class XpathRegressionMissingJavadocModuleTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MissingJavadocModuleCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/missingjavadocmodule";
    }

    @Test
    public void testMissingJavadoc() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
                "module-info/missing/module-info.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocModuleCheck.class);
        final String[] expectedViolation = {
            "2:1: " + getCheckMessage(MissingJavadocModuleCheck.class,
                    MissingJavadocModuleCheck.MSG_MODULE_JAVADOC_MISSING),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/MODULE_DEF",
                "/COMPILATION_UNIT/MODULE_DEF/ANNOTATIONS",
                "/COMPILATION_UNIT/MODULE_DEF/LITERAL_MODULE");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAnnotatedModule() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
                "module-info/annotated/module-info.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocModuleCheck.class);
        final String[] expectedViolation = {
            "2:1: " + getCheckMessage(MissingJavadocModuleCheck.class,
                    MissingJavadocModuleCheck.MSG_MODULE_JAVADOC_MISSING),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/MODULE_DEF",
                "/COMPILATION_UNIT/MODULE_DEF/ANNOTATIONS",
                "/COMPILATION_UNIT/MODULE_DEF/ANNOTATIONS/ANNOTATION[./IDENT[@text='Deprecated']]",
                "/COMPILATION_UNIT/MODULE_DEF/ANNOTATIONS/"
                    + "ANNOTATION[./IDENT[@text='Deprecated']]/AT");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testOpenModule() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
                "module-info/open/module-info.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocModuleCheck.class);
        final String[] expectedViolation = {
            "2:1: " + getCheckMessage(MissingJavadocModuleCheck.class,
                    MissingJavadocModuleCheck.MSG_MODULE_JAVADOC_MISSING),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/MODULE_DEF[./IDENT[@text='example']]",
                "/COMPILATION_UNIT/MODULE_DEF[./IDENT[@text='example']]/ANNOTATIONS",
                "/COMPILATION_UNIT/MODULE_DEF[./IDENT[@text='example']]/LITERAL_OPEN");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
