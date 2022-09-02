///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public class ArchUnitTest {

    /**
     * List of classes that don't follow the rule
     * {@code noClassesInApiShouldDependOnClassesInUtil}.
     */
    private static final Set<String> API_CLASSES_DEPENDENT_ON_UTILS = Set.of(
        "com.puppycrawl.tools.checkstyle.api.FileText",
        "com.puppycrawl.tools.checkstyle.api.AbstractCheck",
        "com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck",
        "com.puppycrawl.tools.checkstyle.api.AutomaticBean$PatternConverter",
        "com.puppycrawl.tools.checkstyle.api.AutomaticBean$RelaxedStringArrayConverter",
        "com.puppycrawl.tools.checkstyle.api.AutomaticBean$UriConverter",
        "com.puppycrawl.tools.checkstyle.api.FileContents"
    );

    @BeforeAll
    public static void init() {
        System.setProperty(
                "org.slf4j.simpleLogger.log.com.tngtech.archunit.core.PluginLoader", "off");
    }

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
     * packages.
     */
    @Test
    public void testClassesInApiDoNotDependOnClassesInUtil() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.api");

        final DescribedPredicate<JavaClass> suppressed = new DescribedPredicate<>("suppressed") {
            @Override
            public boolean apply(JavaClass input) {
                return API_CLASSES_DEPENDENT_ON_UTILS.contains(input.getFullName());
            }
        };

        final ArchRule noClassesInApiShouldDependOnClassesInUtil = noClasses()
            .that(are(not(suppressed)))
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("com.puppycrawl.tools.checkstyle.utils",
                                "com.puppycrawl.tools.checkstyle.checks.javadoc.utils");

        noClassesInApiShouldDependOnClassesInUtil.check(importedClasses);
    }
}
