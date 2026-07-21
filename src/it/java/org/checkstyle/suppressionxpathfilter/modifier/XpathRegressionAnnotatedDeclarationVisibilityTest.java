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
import com.puppycrawl.tools.checkstyle.checks.modifier.AnnotatedDeclarationVisibilityCheck;

public class XpathRegressionAnnotatedDeclarationVisibilityTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotatedDeclarationVisibilityCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/modifier/annotateddeclarationvisibility";
    }

    @Test
    public void testPublicModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedDeclarationVisibilityPublic.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedDeclarationVisibilityCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(AnnotatedDeclarationVisibilityCheck.class,
                    AnnotatedDeclarationVisibilityCheck.MSG_KEY, "public"),
        };

        final String methodXpath =
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedDeclarationVisibilityPublic']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='violationMethod']]";

        final List<String> expectedXpathQueries = List.of(
            methodXpath,
            methodXpath + "/MODIFIERS",
            methodXpath + "/MODIFIERS/ANNOTATION",
            methodXpath + "/MODIFIERS/ANNOTATION/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPrivateModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedDeclarationVisibilityPrivate.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedDeclarationVisibilityCheck.class);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(AnnotatedDeclarationVisibilityCheck.class,
                    AnnotatedDeclarationVisibilityCheck.MSG_KEY, "private"),
        };

        final String methodXpath =
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedDeclarationVisibilityPrivate']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='violationMethod']]";

        final List<String> expectedXpathQueries = List.of(
            methodXpath,
            methodXpath + "/MODIFIERS",
            methodXpath + "/MODIFIERS/ANNOTATION",
            methodXpath + "/MODIFIERS/ANNOTATION/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPackagePrivateModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedDeclarationVisibilityPackagePrivate.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedDeclarationVisibilityCheck.class);

        moduleConfig.addProperty("visibility", "public");

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(AnnotatedDeclarationVisibilityCheck.class,
                    AnnotatedDeclarationVisibilityCheck.MSG_KEY, "package-private"),
        };

        final String methodXpath =
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedDeclarationVisibilityPackagePrivate']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='object']]"
                + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Object']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='violationMethod']]";

        final List<String> expectedXpathQueries = List.of(
            methodXpath,
            methodXpath + "/MODIFIERS",
            methodXpath + "/MODIFIERS/ANNOTATION",
            methodXpath + "/MODIFIERS/ANNOTATION/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNestedClassDeclaration() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedDeclarationVisibilityNestedClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedDeclarationVisibilityCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(AnnotatedDeclarationVisibilityCheck.class,
                    AnnotatedDeclarationVisibilityCheck.MSG_KEY, "public"),
        };

        final String declarationXpath =
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedDeclarationVisibilityNestedClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='ViolationNestedClass']]";

        final List<String> expectedXpathQueries = List.of(
            declarationXpath,
            declarationXpath + "/MODIFIERS",
            declarationXpath + "/MODIFIERS/ANNOTATION",
            declarationXpath + "/MODIFIERS/ANNOTATION/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAnonymousClassField() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathAnnotatedDeclarationVisibilityAnonymousField.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotatedDeclarationVisibilityCheck.class);

        moduleConfig.addProperty("visibility", "public");

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(AnnotatedDeclarationVisibilityCheck.class,
                    AnnotatedDeclarationVisibilityCheck.MSG_KEY, "package-private"),
        };

        final String declarationXpath =
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotatedDeclarationVisibilityAnonymousField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='object']]"
                + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Object']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='violationField']]";

        final List<String> expectedXpathQueries = List.of(
            declarationXpath,
            declarationXpath + "/MODIFIERS",
            declarationXpath + "/MODIFIERS/ANNOTATION",
            declarationXpath + "/MODIFIERS/ANNOTATION/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
