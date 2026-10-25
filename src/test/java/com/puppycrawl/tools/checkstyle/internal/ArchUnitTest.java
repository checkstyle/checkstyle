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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ArchUnitTest {

    /**
     * Suppression list containing violations from {@code classShouldNotDependOnUtilPackages}
     * ArchRule. Location of the violation (eg - {@code in (AbstractAutomaticBean.java:372)})
     * has been omitted as line number can change with modifications to the file.
     */
    private static final List<String> API_PACKAGE_SUPPRESSION_DETAILS = List.of(
        """
        Constructor <com.puppycrawl.tools.checkstyle.api.Violation.<init>(int, int, int, int,\
         java.lang.String, java.lang.String, [Ljava.lang.Object;,\
         com.puppycrawl.tools.checkstyle.api.SeverityLevel, java.lang.String,\
         java.lang.Class, java.lang.String)> calls method\
         <com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil.copyOfArray\
        ([Ljava.lang.Object;, int)>\
        """,
        """
        Method <com.puppycrawl.tools.checkstyle.api.AbstractCheck.log(com.puppycrawl.tools\
        .checkstyle.api.DetailAST, java.lang.String, [Ljava.lang.Object;)> calls method \
        <com.puppycrawl.tools.checkstyle.utils.CommonUtil.lengthExpandedTabs(java.lang\
        .String, int, int)>\
        """,
        """
        Method <com.puppycrawl.tools.checkstyle.api.AbstractCheck.log(int, int, java.lang\
        .String, [Ljava.lang.Object;)> calls method <com.puppycrawl.tools.checkstyle.utils\
        .CommonUtil.lengthExpandedTabs(java.lang.String, int, int)>\
        """,
        """
        Method <com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck.log(int, int, java.lang\
        .String, [Ljava.lang.Object;)> calls method <com.puppycrawl.tools.checkstyle.utils\
        .CommonUtil.lengthExpandedTabs(java.lang.String, int, int)>\
        """,
        """
        Method <com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck.process(java.io.File, \
        com.puppycrawl.tools.checkstyle.api.FileText)> calls method <com.puppycrawl.tools\
        .checkstyle.utils.CommonUtil.matchesFileExtension(java.io.File, [Ljava.lang\
        .String;)>\
        """
    );

    /**
     * List of checks not following {@link #testFileStatefulCheckOverridesBeginTree()} rule.
     * until #22175.
     */
    // until https://github.com/checkstyle/checkstyle/issues/22175
    private static final Set<String> SUPPRESSED_CHECKS_WITHOUT_BEGIN_TREE = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.CovariantEqualsCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding."
            + "NoCauseGetMessageInExceptionConstructorCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck",
        "com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck",
        "com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck",
        "com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck"
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

        final List<String> allDescriptions = result.getFailureReport().getDetails();
        final List<String> outdatedSuppressions = API_PACKAGE_SUPPRESSION_DETAILS.stream()
                .filter(suppression -> {
                    return allDescriptions.stream()
                            .noneMatch(description -> description.startsWith(suppression));
                })
                .toList();

        assertWithMessage("Outdated suppressions (can be removed)")
                .that(outdatedSuppressions)
                .isEmpty();

        final EvaluationResult filtered = result.filterDescriptionsMatching(description -> {
            return API_PACKAGE_SUPPRESSION_DETAILS.stream()
                .noneMatch(description::startsWith);
        });

        assertWithMessage("api package: %s", classShouldNotDependOnUtilPackages.getDescription())
            .that(filtered.getFailureReport().getDetails())
            .isEmpty();
    }

    /**
     * Tests that all checks annotated with {@link FileStatefulCheck} override the
     * {@code beginTree} method to reset state between files.
     */
    @Test
    public void testFileStatefulCheckOverridesBeginTree() {
        final JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.puppycrawl.tools.checkstyle.checks");

        final SuppressionArchCondition beSuppressedClass =
                new SuppressionArchCondition(
                        SUPPRESSED_CHECKS_WITHOUT_BEGIN_TREE, "be suppressed");

        final ArchRule fileStatefulChecksShouldOverrideBeginTree = classes()
            .that()
            .areAssignableTo(AbstractCheck.class)
            .and()
            .areAnnotatedWith(FileStatefulCheck.class)
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .should(overrideBeginTree())
            .orShould(beSuppressedClass);

        fileStatefulChecksShouldOverrideBeginTree.check(importedClasses);

        assertWithMessage("Outdated suppressions (can be removed)")
                .that(beSuppressedClass.suppressions)
                .containsExactlyElementsIn(beSuppressedClass.usedSuppressions);
    }

    /**
     * ArchCondition checking that a class overrides the {@code beginTree} method.
     *
     * @return ArchCondition checking that a class overrides the {@code beginTree} method
     */
    private static ArchCondition<JavaClass> overrideBeginTree() {
        return new OverrideBeginTreeArchCondition();
    }

    /**
     * ArchCondition checking that a class overrides the {@code beginTree} method.
     */
    private static final class OverrideBeginTreeArchCondition extends ArchCondition<JavaClass> {

        private OverrideBeginTreeArchCondition() {
            super("override beginTree method");
        }

        @Override
        public void check(JavaClass item, ConditionEvents events) {
            final boolean hasBeginTree = item.getAllMethods().stream()
                .filter(method -> {
                    return "beginTree".equals(method.getName())
                        && method.getRawParameterTypes().size() == 1
                        && method.getRawParameterTypes().getFirst()
                            .isEquivalentTo(DetailAST.class);
                })
                .anyMatch(method -> {
                    return !method.getOwner().isEquivalentTo(AbstractCheck.class);
                });

            if (!hasBeginTree) {
                final String message = String.format(Locale.ROOT,
                    "<%s> does not override beginTree method",
                    item.getFullName());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }

    }

    /**
     * ArchCondition checking if a type is present in the suppression list.
     */
    private static final class SuppressionArchCondition extends ArchCondition<JavaClass> {

        private final Set<String> suppressions;
        private final Set<String> usedSuppressions;

        private SuppressionArchCondition(Set<String> suppressions, String description) {
            super(description);
            this.suppressions = suppressions;
            usedSuppressions = new HashSet<>();
        }

        @Override
        public void check(JavaClass item, ConditionEvents events) {
            final String fullName = item.getFullName();
            if (suppressions.contains(fullName)) {
                usedSuppressions.add(fullName);
            }
            else {
                final String message = String.format(
                    Locale.ROOT, "should %s or resolved.", getDescription());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }

    }

}
