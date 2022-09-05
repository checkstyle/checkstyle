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

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.domain.properties.HasName;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ArchUnitTest {

    private static final JavaClasses CHECKS_PACKAGE = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.puppycrawl.tools.checkstyle.checks");

    /**
     * Classes not abiding to {@code checksShouldExtendAbstractCheckOrAbstractFileSetCheck} rule.
     */
    private static final Set<String> SUPPRESSED_CLASSES = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck",
        "com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.RecordTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LambdaParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheck",
        "com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.IllegalIdentifierNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.RecordComponentNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.AbstractAccessControlNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.SuperCloneCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck",
        "com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck"
    );

    @BeforeAll
    public static void init() {
        System.setProperty(
                "org.slf4j.simpleLogger.log.com.tngtech.archunit.core.PluginLoader", "off");
    }

    /**
     * ArchCondition checking that a given class extends the given superclass.
     *
     * @param superclass the superclass
     * @return ArchCondition checking that a given class extends the given superclass
     */
    private static ArchCondition<JavaClass> extend(Class<?> superclass) {
        return new SuperclassArchCondition(superclass);
    }

    /**
     * The goal is to ensure all classes of a specific name pattern have non-protected methods,
     * except for those which are annotated with {@code Override}. In the bytecode there is no
     * trace anymore if this method was annotated with {@code Override} or not (limitation of
     * Archunit), eventually we need to make checkstyle's Check on this.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *      but not in this method
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

        final ArchRule checkMethodsShouldNotBeProtectedRule =
                methods().that()
                .haveNameNotMatching(".*(" + ignoreMethodList + ")").and()
                .areDeclaredInClassesThat()
                .haveSimpleNameEndingWith("Check").and()
                .areDeclaredInClassesThat()
                .doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().notBeProtected();

        checkMethodsShouldNotBeProtectedRule.check(CHECKS_PACKAGE);
    }

    /**
     * Tests that all checks either extend {@link AbstractCheck} or {@link AbstractFileSetCheck}.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void testChecksShouldExtendAbstractCheckOrAbstractFileSetCheck() {
        final ArchCondition<JavaClass> beSuppressedClass = new SuppressionArchCondition<>(
            SUPPRESSED_CLASSES, "be suppressed");

        final ArchRule checksShouldExtendCorrectAbstractClass = classes()
            .that()
            .haveSimpleNameEndingWith("Check")
            .should(extend(AbstractCheck.class))
            .orShould(extend(AbstractFileSetCheck.class))
            .orShould(beSuppressedClass);

        checksShouldExtendCorrectAbstractClass.check(CHECKS_PACKAGE);
    }

    /**
     * ArchCondition checking that a given class extends the expectedSuperclass.
     */
    private static final class SuperclassArchCondition extends ArchCondition<JavaClass> {

        private final Class<?> expectedSuperclass;

        private SuperclassArchCondition(Class<?> expectedSuperclass) {
            super("extend " + expectedSuperclass.getSimpleName());
            this.expectedSuperclass = expectedSuperclass;
        }

        @Override
        public void check(JavaClass item, ConditionEvents events) {
            final Optional<JavaType> superclassOptional = item.getSuperclass();
            if (superclassOptional.isPresent()) {
                final JavaClass superclass = superclassOptional.get().toErasure();
                if (!superclass.isEquivalentTo(expectedSuperclass)) {
                    final String format = "<%s> extends <%s> instead of <%s>";
                    final String message = String
                        .format(Locale.ROOT, format, item.getFullName(), superclass.getFullName(),
                                expectedSuperclass.getName());
                    events.add(SimpleConditionEvent.violated(item, message));
                }
            }
        }
    }

    /**
     * ArchCondition checking if a type or a member is present in the suppression list.
     */
    private static final class SuppressionArchCondition<T extends HasName.AndFullName>
        extends ArchCondition<T> {

        private final Set<String> suppressions;

        private SuppressionArchCondition(Set<String> suppressions, String description) {
            super(description);
            this.suppressions = suppressions;
        }

        @Override
        public void check(HasName.AndFullName item, ConditionEvents events) {
            if (!suppressions.contains(item.getFullName())) {
                final String message = String.format(
                    Locale.ROOT, "should %s or resolved.", getDescription());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }
    }
}
