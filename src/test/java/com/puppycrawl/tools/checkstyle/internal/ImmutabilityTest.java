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

import static com.tngtech.archunit.base.DescribedPredicate.doNot;
import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.have;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.meta.ModuleDetails;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.meta.XmlMetaReader;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaParameterizedType;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.domain.properties.HasName;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ImmutabilityTest {

    /**
     * Immutable types canonical names.
     */
    private static final Set<String> IMMUTABLE_TYPES = Set.of(
        "java.lang.String",
        "java.lang.Integer",
        "java.lang.Byte",
        "java.lang.Character",
        "java.lang.Short",
        "java.lang.Boolean",
        "java.lang.Long",
        "java.lang.Double",
        "java.lang.Float",
        "java.lang.StackTraceElement",
        "java.math.BigInteger",
        "java.math.BigDecimal",
        "java.io.File",
        "java.util.Locale",
        "java.util.UUID",
        "java.net.URL",
        "java.net.URI",
        "java.net.Inet4Address",
        "java.net.Inet6Address",
        "java.net.InetSocketAddress",
        "java.util.regex.Pattern"
    );

    /**
     * Immutable primitive types.
     */
    private static final Set<String> PRIMITIVE_TYPES = Set.of(
        "byte",
        "short",
        "int",
        "long",
        "float",
        "double",
        "char",
        "boolean"
    );

    /**
     * List of fields that are a zero size array. They are immutable by definition.
     */
    private static final Set<String> ZERO_SIZE_ARRAY_FIELDS = Set.of(
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_BIT_SET",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_BYTE_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_DOUBLE_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_INTEGER_OBJECT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_INT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_OBJECT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY"
    );

    /**
     * List of fields not following {@link #testUtilClassesImmutability()} rule.
     */
    private static final Set<String> SUPPRESSED_FIELDS_IN_UTIL_CLASSES = Set.of(
        "com.puppycrawl.tools.checkstyle.utils.TokenUtil.TOKEN_IDS",
        "com.puppycrawl.tools.checkstyle.utils.XpathUtil.TOKEN_TYPES_WITH_TEXT_ATTRIBUTE"
    );

    /**
     * List of fields not following {@link #testFieldsInStatelessChecksShouldBeImmutable()} rule.
     */
    private static final Set<String> SUPPRESSED_FIELDS_IN_MODULES = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck.primitiveDataTypes",
        "com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder.ENTRIES",
        "com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.TYPES_HASH_SET",
        "com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck"
            + ".HAS_MEMBERS",
        "com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck"
            + ".IGNORED_TYPES",
        "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck"
            + ".ALLOWED_ASSIGNMENT_CONTEXT",
        "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck"
            + ".ALLOWED_ASSIGNMENT_IN_COMPARISON_CONTEXT",
        "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck.COMPARISON_TYPES",
        "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck.CONTROL_CONTEXT",
        "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck"
            + ".LOOP_IDIOM_IGNORED_PARENTS",
        "com.puppycrawl.tools.checkstyle.checks.coding.MatchXpathCheck.xpathExpression",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.DEFAULT_ORDER",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck.DEFAULT_TAGS",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.ALLOWED_TYPES",
        "com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.JLS_ORDER",
        "com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck"
            + ".TOKENS_FOR_INTERFACE_MODIFIERS",
        "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck.detector",
        "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck.detector",
        "com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck.formatString",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.tagRegExp",
        "com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.format",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.option"
    );

    /**
     * List of classes not following
     * {@link #testClassesWithImmutableFieldsShouldBeStateless()} rule.
     */
    private static final Set<String> SUPPRESSED_CLASSES_FOR_STATELESS_CHECK_RULE = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck",
        "com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck",
        "com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck",
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
        "com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck",
        "com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck"
    );

    /**
     * List of classes not following {@link #testClassesWithMutableFieldsShouldBeStateful()} rule.
     */
    private static final Set<String> SUPPRESSED_CLASSES_FOR_STATEFUL_CHECK_RULE = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck"
    );

    private static final JavaClasses CHECKSTYLE_CHECKS = new ClassFileImporter()
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

    /**
     * ArchCondition for immutable fields.
     */
    private static final ArchCondition<JavaField> BE_IMMUTABLE = new ImmutableFieldArchCondition();

    /**
     * DescribedPredicate defining condition for a class to have immutable fields.
     */
    private static final DescribedPredicate<JavaClass> IMMUTABLE_FIELDS =
        new ImmutableFieldsPredicate();

    /**
     * Map of module full name to module details.
     */
    private static final Map<String, ModuleDetails> MODULE_DETAILS_MAP =
        XmlMetaReader.readAllModulesIncludingThirdPartyIfAny().stream()
            .collect(Collectors.toUnmodifiableMap(ModuleDetails::getFullQualifiedName,
                                      Function.identity()));

    /**
     * Test to ensure that fields in util classes are immutable.
     */
    @Test
    public void testUtilClassesImmutability() {
        final JavaClasses utilClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.utils",
                            "com.puppycrawl.tools.checkstyle.checks.javadoc.utils");

        final ArchCondition<JavaField> beSuppressedField = new SuppressionArchCondition<>(
            SUPPRESSED_FIELDS_IN_UTIL_CLASSES, "be suppressed");

        final ArchRule fieldsInUtilClassesShouldBeImmutable = fields()
            .that()
            .areDeclaredInClassesThat()
            .haveSimpleNameEndingWith("Util")
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal()
            .andShould()
            .beStatic()
            .orShould(beSuppressedField);

        fieldsInUtilClassesShouldBeImmutable.check(utilClasses);
    }

    /**
     * Test to ensure modules annotated with {@link StatelessCheck} contain immutable fields.
     */
    @Test
    public void testFieldsInStatelessChecksShouldBeImmutable() {
        final DescribedPredicate<JavaField> moduleProperties = new ModulePropertyPredicate();

        final ArchCondition<JavaField> beSuppressedField = new SuppressionArchCondition<>(
            SUPPRESSED_FIELDS_IN_MODULES, "be suppressed");

        final ArchRule fieldsInStatelessChecksShouldBeImmutable = fields()
            .that()
            .haveNameNotContaining("$")
            .and()
            .areDeclaredInClassesThat()
            .areAnnotatedWith(StatelessCheck.class)
            .and(are(not(moduleProperties)))
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal()
            .orShould(beSuppressedField);

        fieldsInStatelessChecksShouldBeImmutable.check(CHECKSTYLE_CHECKS);
    }

    /**
     * Test to ensure classes with immutable fields are annotated with {@link StatelessCheck}.
     */
    @Test
    public void testClassesWithImmutableFieldsShouldBeStateless() {
        final ArchCondition<JavaClass> beSuppressedClass = new SuppressionArchCondition<>(
            SUPPRESSED_CLASSES_FOR_STATELESS_CHECK_RULE, "be suppressed");

        final ArchRule classesWithImmutableFieldsShouldBeStateless = classes()
            .that(have(IMMUTABLE_FIELDS))
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .should()
            .beAnnotatedWith(StatelessCheck.class)
            .orShould(beSuppressedClass);

        classesWithImmutableFieldsShouldBeStateless.check(CHECKSTYLE_CHECKS);
    }

    /**
     * Test to ensure classes with mutable fields are annotated with {@link FileStatefulCheck} or
     * {@link GlobalStatefulCheck}.
     */
    @Test
    public void testClassesWithMutableFieldsShouldBeStateful() {
        final ArchCondition<JavaClass> beSuppressedClass = new SuppressionArchCondition<>(
            SUPPRESSED_CLASSES_FOR_STATEFUL_CHECK_RULE, "be suppressed");

        final ArchRule classesWithMutableFieldsShouldBeStateful = classes()
            .that(doNot(have(IMMUTABLE_FIELDS)))
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .should()
            .beAnnotatedWith(FileStatefulCheck.class)
            .orShould()
            .beAnnotatedWith(GlobalStatefulCheck.class)
            .orShould(beSuppressedClass);

        classesWithMutableFieldsShouldBeStateful.check(CHECKSTYLE_CHECKS);
    }

    /**
     * ArchCondition checking fields are immutable.
     */
    private static final class ImmutableFieldArchCondition extends ArchCondition<JavaField> {
        private ImmutableFieldArchCondition() {
            super("be among immutable types");
        }

        /**
         * Whether the raw type of the field is immutable.
         *
         * @param javaField java field to examine
         * @return {@code true} if the raw type of field is immutable.
         */
        private static boolean isRawTypeImmutable(JavaField javaField) {
            final JavaClass rawType = javaField.getRawType();
            final String rawTypeName = rawType.getName();
            return PRIMITIVE_TYPES.contains(rawTypeName)
                || IMMUTABLE_TYPES.contains(rawTypeName);
        }

        /**
         * Whether the field is an enum constant or an empty array.
         *
         * @param javaField java field to examine
         * @return {@code true} if the field is an enum constant or an empty array
         */
        private static boolean isEnumConstantOrEmptyArray(JavaField javaField) {
            final JavaClass rawType = javaField.getRawType();
            return rawType.isEnum()
                || ZERO_SIZE_ARRAY_FIELDS.contains(javaField.getFullName());
        }

        /**
         * Whether the parameterized type of a field is immutable if it contains parameterized
         * type. Parameterized type refers to the generic type of a field.
         * {@code List<String>}, here the concrete type of parameterized field is
         * {@code java.lang.String}.
         *
         * @param javaField java field to examine
         * @return {@code true} if the parameterized type of a field is immutable
         *         if it contains parameterized type
         */
        private static boolean isParameterizedTypeImmutable(JavaField javaField) {
            boolean isParameterizedTypeImmutable = false;
            final JavaType javaType = javaField.getType();

            if (javaType instanceof JavaParameterizedType) {
                final JavaParameterizedType parameterizedType = (JavaParameterizedType) javaType;
                isParameterizedTypeImmutable = parameterizedType.getActualTypeArguments().stream()
                    .allMatch(actualTypeArgument -> {
                        return IMMUTABLE_TYPES.contains(actualTypeArgument.toErasure().getName());
                    });
            }
            return isParameterizedTypeImmutable;
        }

        @Override
        public void check(JavaField item, ConditionEvents events) {
            if (!isRawTypeImmutable(item)
                && !isEnumConstantOrEmptyArray(item)
                && !isParameterizedTypeImmutable(item)) {
                final String message = String
                    .format(Locale.ROOT, "Field <%s> should %s in %s",
                            item.getFullName(), getDescription(),
                            item.getSourceCodeLocation());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }
    }

    /**
     * DescribedPredicate defining condition for a field to be a module property.
     */
    private static final class ModulePropertyPredicate extends DescribedPredicate<JavaField> {

        private ModulePropertyPredicate() {
            super("module properties");
        }

        /**
         * Whether a field is a module property or not.
         *
         * @param javaField field to check
         * @return {@code true} if field is a module property
         */
        private static boolean isModuleProperty(JavaField javaField) {
            boolean result = false;
            final JavaClass containingClass = javaField.getOwner();
            final ModuleDetails moduleDetails = MODULE_DETAILS_MAP.get(
                containingClass.getFullName());
            if (moduleDetails != null) {
                final List<ModulePropertyDetails> properties = moduleDetails.getProperties();
                result = properties.stream()
                    .map(ModulePropertyDetails::getName)
                    .anyMatch(moduleName -> moduleName.equals(javaField.getName()));
            }
            return result;
        }

        @Override
        public boolean test(JavaField input) {
            return isModuleProperty(input);
        }
    }

    /**
     * DescribedPredicate defining condition for a class to have immutable fields.
     */
    private static final class ImmutableFieldsPredicate extends DescribedPredicate<JavaClass> {
        private ImmutableFieldsPredicate() {
            super("immutable fields");
        }

        @Override
        public boolean test(JavaClass input) {
            final Set<JavaField> fields = input.getFields();
            return fields.stream()
                .filter(javaField -> {
                    return !ModulePropertyPredicate.isModuleProperty(javaField)
                        && !javaField.getName().contains("$")
                        && !SUPPRESSED_FIELDS_IN_MODULES.contains(javaField.getFullName());
                })
                .allMatch(javaField -> {
                    final Set<JavaModifier> javaFieldModifiers = javaField.getModifiers();
                    return javaFieldModifiers.contains(JavaModifier.FINAL)
                        && (ImmutableFieldArchCondition.isRawTypeImmutable(javaField)
                            || ImmutableFieldArchCondition.isEnumConstantOrEmptyArray(javaField)
                            || ImmutableFieldArchCondition.isParameterizedTypeImmutable(javaField));
                });
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
