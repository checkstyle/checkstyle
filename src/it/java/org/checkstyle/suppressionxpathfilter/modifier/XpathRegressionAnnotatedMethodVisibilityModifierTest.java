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
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.modifier.AnnotatedMethodVisibilityModifierCheck;

public class XpathRegressionAnnotatedMethodVisibilityModifierTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotatedMethodVisibilityModifierCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/modifier/annotatedmethodvisibilitymodifier";
    }

    @Test
    public void testPublicModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedMethodVisibilityModifierPublic.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedMethodVisibilityModifierCheck.class);

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(AnnotatedMethodVisibilityModifierCheck.class,
                    AnnotatedMethodVisibilityModifierCheck.MSG_KEY, "public"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedMethodVisibilityModifierPublic']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='violationMethod']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPrivateModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedMethodVisibilityModifierPrivate.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedMethodVisibilityModifierCheck.class);

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(AnnotatedMethodVisibilityModifierCheck.class,
                    AnnotatedMethodVisibilityModifierCheck.MSG_KEY, "private"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathAnnotatedMethodVisibilityModifierPrivate']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='violationMethod']]"
                    + "/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPackagePrivateModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedMethodVisibilityModifierPackagePrivate.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedMethodVisibilityModifierCheck.class);

        moduleConfig.addProperty("visibility", "public");

        final String[] expectedViolation = {
            "6:10: " + getCheckMessage(AnnotatedMethodVisibilityModifierCheck.class,
                    AnnotatedMethodVisibilityModifierCheck.MSG_KEY, "package-private"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathAnnotatedMethodVisibilityModifierPackagePrivate']]"
                    + "/OBJBLOCK/METHOD_DEF/IDENT[@text='violationMethod']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
