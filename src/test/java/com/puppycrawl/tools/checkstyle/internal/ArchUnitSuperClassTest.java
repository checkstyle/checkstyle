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

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.domain.properties.HasName;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ArchUnitSuperClassTest {

    /**
     * Classes not abiding to {@link #testChecksShouldHaveAllowedAbstractClassAsSuperclass()} rule.
     */
    private static final Set<String> SUPPRESSED_CLASSES = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.coding.SuperCloneCheck",
        "com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheck",
        "com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
        "com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.AbstractAccessControlNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.IllegalIdentifierNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LambdaParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.RecordComponentNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.RecordTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck"
    );

    /**
     * ArchCondition checking that a class is the direct subclass of a particular class.
     *
     * @param superclass the superclass
     * @return ArchCondition checking that a class is the direct subclass of a particular class
     */
    private static ArchCondition<JavaClass> beDirectSubclassOf(Class<?> superclass) {
        return new SuperclassArchCondition(superclass);
    }

    /**
     * Tests that all checks have {@link AbstractCheck} or {@link AbstractFileSetCheck} or
     * {@link AbstractJavadocCheck} as their super class.
     */
    @Test
    public void testChecksShouldHaveAllowedAbstractClassAsSuperclass() {
        final JavaClasses checksPackage = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle")
            .that(new DescribedPredicate<>("are checkstyle modules") {
                @Override
                public boolean test(JavaClass input) {
                    final Class<?> clazz = input.reflect();
                    return ModuleReflectionUtil.isCheckstyleModule(clazz)
                        && (ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(clazz)
                            || ModuleReflectionUtil.isFileSetModule(clazz));
                }
            });

        final ArchCondition<JavaClass> beSuppressedClass = new SuppressionArchCondition<>(
            SUPPRESSED_CLASSES, "be suppressed");

        final ArchRule checksShouldHaveAllowedAbstractClassAsSuper = classes()
            .should(beDirectSubclassOf(AbstractCheck.class)
                        .or(beDirectSubclassOf(AbstractFileSetCheck.class))
                        .or(beDirectSubclassOf(AbstractJavadocCheck.class)))
            .orShould(beSuppressedClass);

        checksShouldHaveAllowedAbstractClassAsSuper.check(checksPackage);
    }

    /**
     * ArchCondition checking that a given class extends the expected superclass.
     */
    private static final class SuperclassArchCondition extends ArchCondition<JavaClass> {

        private final Class<?> expectedSuperclass;

        private SuperclassArchCondition(Class<?> expectedSuperclass) {
            super("be subclass of " + expectedSuperclass.getSimpleName());
            this.expectedSuperclass = expectedSuperclass;
        }

        @Override
        public void check(JavaClass item, ConditionEvents events) {
            final Optional<JavaType> superclassOptional = item.getSuperclass();
            if (superclassOptional.isPresent()) {
                final JavaClass superclass = superclassOptional.get().toErasure();
                if (!superclass.isEquivalentTo(expectedSuperclass)) {
                    final String format = "<%s> is subclass of <%s> instead of <%s>";
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
