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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;

public class ArchUnitTest {

    /**
     * Suppression list containing violations from {@code classShouldNotDependOnUtilPackages}
     * ArchRule. Location of the violation (eg - {@code in (AbstractAutomaticBean.java:372)})
     * has been omitted as line number can change with modifications to the file.
     */
    private static final List<String> API_PACKAGE_SUPPRESSION_DETAILS = List.of(
        "Constructor <com.puppycrawl.tools.checkstyle.api.FileText.<init>(java.io.File, java.lang"
            + ".String)> gets field <com.puppycrawl.tools.checkstyle.utils.CommonUtil"
            + ".EMPTY_STRING_ARRAY>",
        "Constructor <com.puppycrawl.tools.checkstyle.api.Violation.<init>(int, int, int, int,"
            + " java.lang.String, java.lang.String, [Ljava.lang.Object;,"
            + " com.puppycrawl.tools.checkstyle.api.SeverityLevel, java.lang.String,"
            + " java.lang.Class, java.lang.String)> calls method"
            + " <com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil.copyOfArray"
            + "([Ljava.lang.Object;, int)>",
        "Constructor <com.puppycrawl.tools.checkstyle.api.FileText.<init>(java.io.File, java.util"
            + ".List)> gets field <com.puppycrawl.tools.checkstyle.utils.CommonUtil"
            + ".EMPTY_STRING_ARRAY>",
        "Method <com.puppycrawl.tools.checkstyle.api.AbstractCheck.log(com.puppycrawl.tools"
            + ".checkstyle.api.DetailAST, java.lang.String, [Ljava.lang.Object;)> calls method "
            + "<com.puppycrawl.tools.checkstyle.utils.CommonUtil.lengthExpandedTabs(java.lang"
            + ".String, int, int)>",
        "Method <com.puppycrawl.tools.checkstyle.api.AbstractCheck.log(int, int, java.lang"
            + ".String, [Ljava.lang.Object;)> calls method <com.puppycrawl.tools.checkstyle.utils"
            + ".CommonUtil.lengthExpandedTabs(java.lang.String, int, int)>",
        "Method <com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck.log(int, int, java.lang"
            + ".String, [Ljava.lang.Object;)> calls method <com.puppycrawl.tools.checkstyle.utils"
            + ".CommonUtil.lengthExpandedTabs(java.lang.String, int, int)>",
        "Method <com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck.process(java.io.File, "
            + "com.puppycrawl.tools.checkstyle.api.FileText)> calls method <com.puppycrawl.tools"
            + ".checkstyle.utils.CommonUtil.matchesFileExtension(java.io.File, [Ljava.lang"
            + ".String;)>",
        "Method <com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck.setFileExtensions"
            + "([Ljava.lang.String;)> calls method <com.puppycrawl.tools.checkstyle.utils"
            + ".CommonUtil.startsWithChar(java.lang.String, char)>",
        "Method <com.puppycrawl.tools.checkstyle.AbstractAutomaticBean$PatternConverter"
            + ".convert(java.lang.Class, java.lang.Object)> calls method <com.puppycrawl.tools"
            + ".checkstyle.utils.CommonUtil.createPattern(java.lang.String)>",
        "Method <com.puppycrawl.tools.checkstyle.AbstractAutomaticBean$RelaxedStringArray"
            + "Converter.convert(java.lang.Class, java.lang.Object)> gets field <com.puppycrawl"
            + ".tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY>",
        "Method <com.puppycrawl.tools.checkstyle.AbstractAutomaticBean$UriConverter.convert("
            + "java.lang.Class, java.lang.Object)> calls method <com.puppycrawl.tools.checkstyle"
            + ".utils.CommonUtil.getUriByFilename(java.lang.String)>",
        "Method <com.puppycrawl.tools.checkstyle.AbstractAutomaticBean$UriConverter.convert("
            + "java.lang.Class, java.lang.Object)> calls method <com.puppycrawl.tools.checkstyle"
            + ".utils.CommonUtil.isBlank(java.lang.String)>",
        "Method <com.puppycrawl.tools.checkstyle.api.FileContents.lineIsBlank(int)> calls method "
            + "<com.puppycrawl.tools.checkstyle.utils.CommonUtil.isBlank(java.lang.String)>"
    );

    /**
     * The goal is to ensure all classes of a specific name pattern have non-protected methods,
     * except for those which are annotated with {@code Override}. In the bytecode there is no
     * trace anymore if this method was annotated with {@code Override} or not (limitation of
     * Archunit), eventually we need to make checkstyle's Check on this.
     */
    @Test
    public void nonProtectedCheckMethodsTest() {
        // This list contains methods which have been overridden and are set to ignore in this test.
        final String[] methodsWithOverrideAnnotation = {
            "processFiltered",
            "getMethodName",
            "mustCheckName",
            "postProcessHeaderLines",
            "getLogMessageId",
        };
        final String ignoreMethodList = String.join("|", methodsWithOverrideAnnotation);
        final JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.puppycrawl.tools.checkstyle.checks");

        final ArchRule checkMethodsShouldNotBeProtectedRule =
                methods().that()
                .haveNameNotMatching(".*(" + ignoreMethodList + ")").and()
                .areDeclaredInClassesThat()
                .haveSimpleNameEndingWith("Check").and()
                .areDeclaredInClassesThat()
                .doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().notBeProtected();

        checkMethodsShouldNotBeProtectedRule.check(importedClasses);
    }

    /**
     * The goal is to ensure all classes in api package are not dependent on classes in util
     * packages. Changes in Util classes are not considered to be breaking changes as they are
     * "internal". Therefore classes in api should not depend on them.
     */
    @Test
    public void testClassesInApiDoNotDependOnClassesInUtil() {
        final JavaClasses apiPackage = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.api");

        final String[] utilPackages = {
            "com.puppycrawl.tools.checkstyle.utils",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.utils",
        };

        final ArchRule classShouldNotDependOnUtilPackages = noClasses()
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(utilPackages);

        final EvaluationResult result = classShouldNotDependOnUtilPackages.evaluate(apiPackage);
        final EvaluationResult filtered = result.filterDescriptionsMatching(description -> {
            return API_PACKAGE_SUPPRESSION_DETAILS.stream()
                .noneMatch(description::startsWith);
        });

        assertWithMessage("api package: " + classShouldNotDependOnUtilPackages.getDescription())
            .that(filtered.getFailureReport().getDetails())
            .isEmpty();
    }

}
