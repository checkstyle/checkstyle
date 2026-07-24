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

package org.checkstyle.suppressionxpathfilter.modifier;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCompactSourceCheck;

public class XpathRegressionRedundantModifierCompactSourceTest
        extends AbstractXpathTestSupport {

    private static final Class<RedundantModifierCompactSourceCheck> CLAZZ =
            RedundantModifierCompactSourceCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/modifier/"
                + "redundantmodifiercompactsource";
    }

    @Test
    public void testFinalModifier() throws Exception {
        final File fileToProcess = new File(
                getNonCompilablePath("InputXpathRedundantModifierCompactSource.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);
        final String[] expected = {
            "2:1: " + getCheckMessage(CLAZZ,
                    RedundantModifierCompactSourceCheck.MSG_KEY, "final"),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPACT_COMPILATION_UNIT",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='helper']]",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='helper']]"
                        + "/MODIFIERS",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='helper']]"
                        + "/MODIFIERS/FINAL");
        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testStrictfpModifier() throws Exception {
        final File fileToProcess = new File(
                getNonCompilablePath("InputXpathRedundantModifierCompactSourceStrictfp.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);
        final String[] expected = {
            "2:1: " + getCheckMessage(CLAZZ,
                    RedundantModifierCompactSourceCheck.MSG_KEY, "strictfp"),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPACT_COMPILATION_UNIT",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='calculate']]",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='calculate']]"
                        + "/MODIFIERS",
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='calculate']]"
                        + "/MODIFIERS/STRICTFP");
        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testAnnotatedMethod() throws Exception {
        final File fileToProcess = new File(
                getNonCompilablePath("InputXpathRedundantModifierCompactSourceAnnotated.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);
        final String[] expected = {
            "3:1: " + getCheckMessage(CLAZZ,
                    RedundantModifierCompactSourceCheck.MSG_KEY, "final"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='annotated']]"
                        + "/MODIFIERS/FINAL");
        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

}
