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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.meta.ModuleDetails;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.meta.XmlMetaReader;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaParameterizedType;
import com.tngtech.archunit.core.domain.JavaType;
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
     * List of suppressed fields in util classes.
     */
    private static final Set<String> SUPPRESSED_FIELDS_IN_UTIL_CLASSES = Set.of(
        "com.puppycrawl.tools.checkstyle.utils.TokenUtil.TOKEN_IDS",
        "com.puppycrawl.tools.checkstyle.utils.XpathUtil.TOKEN_TYPES_WITH_TEXT_ATTRIBUTE"
    );

    /**
     * List of fields not following {@code testFieldsInStatlessCheckAreImmutable} ArchRule.
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
     * List of classes not following {@code testClassesWithImmutableFieldsAreStateless} and
     * {@code testClassesWithoutImmutableFieldsAreStateful} ArchRule.
     */
    private static final Set<String> SUPPRESSED_CLASSES = Set.of(
        // Failing 'testClassesWithImmutableFieldsAreStateless'
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
        "com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck",
        "com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck",
        // Failing 'testClassesWithoutImmutableFieldsAreStateful'
        "com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck"
    );

    /**
     * ArchCondition for immutable fields.
     */
    private static final ArchCondition<JavaField> BE_IMMUTABLE = new ImmutableFieldArchCondition();

    /**
     * Map fo module full name to module details.
     */
    private static final Map<String, ModuleDetails> MODULE_DETAILS_MAP =
        XmlMetaReader.readAllModulesIncludingThirdPartyIfAny();

    /**
     * Test to ensure that fields in util classes are immutable.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void testUtilClassesImmutability() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.utils",
                            "com.puppycrawl.tools.checkstyle.checks.javadoc.utils");

        final DescribedPredicate<JavaField> suppressed =
            new DescribedPredicate<>("suppressed") {
                @Override
                public boolean apply(JavaField input) {
                    return SUPPRESSED_FIELDS_IN_UTIL_CLASSES.contains(input.getFullName());
                }
            };

        final DescribedPredicate<JavaField> zeroSizeArrays =
            new DescribedPredicate<>("zero size arrays") {
                @Override
                public boolean apply(JavaField input) {
                    return ZERO_SIZE_ARRAY_FIELDS.contains(input.getFullName());
                }
            };

        final ArchRule testUtilClassesImmutability = fields()
            .that(are(not(suppressed)).and(are(not(zeroSizeArrays))))
            .and()
            .areDeclaredInClassesThat()
            .haveSimpleNameEndingWith("Util")
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal()
            .andShould()
            .beStatic();

        testUtilClassesImmutability.check(importedClasses);
    }

    /**
     * Test to ensure modules annotated with {@link StatelessCheck} contain immutable fields
     * and modules with mutable fields are annotated with {@link FileStatefulCheck} or
     * {@link GlobalStatefulCheck}.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void testModuleImmutability() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.checks");

        final ModulePropertyPredicate moduleProperties = new ModulePropertyPredicate();

        final DescribedPredicate<JavaField> presentInFieldSuppressionList =
            new DescribedPredicate<>("present in field suppression list") {
                @Override
                public boolean apply(JavaField input) {
                    return SUPPRESSED_FIELDS_IN_MODULES.contains(input.getFullName());
                }
            };

        final DescribedPredicate<JavaClass> presentInClassSuppressionList =
            new DescribedPredicate<>("present in class suppression list") {
                @Override
                public boolean apply(JavaClass input) {
                    return SUPPRESSED_CLASSES.contains(input.getFullName());
                }
            };

        final DescribedPredicate<JavaClass> immutableFields = new DescribedPredicate<>(
            "immutable fields") {
            @Override
            public boolean apply(JavaClass input) {
                final Set<JavaField> fields = input.getFields();
                return fields.stream()
                    .filter(javaField -> {
                        return !ModulePropertyPredicate.isModuleProperty(javaField)
                            && !SUPPRESSED_FIELDS_IN_MODULES.contains(javaField.getFullName());
                    })
                    .allMatch(javaField -> {
                        return javaField.getModifiers().contains(JavaModifier.FINAL)
                            && !ImmutableFieldArchCondition.isMutable(javaField);
                    });
            }
        };

        final ArchRule testFieldsInStatlessCheckAreImmutable = fields()
            .that()
            .areDeclaredInClassesThat()
            .areAnnotatedWith(StatelessCheck.class)
            .and(are(not(presentInFieldSuppressionList)).and(are(not(moduleProperties))))
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal();

        final ArchRule testClassesWithImmutableFieldsAreStateless = classes()
            .that(have(immutableFields))
            .and()
            .haveSimpleNameEndingWith("Check")
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .and(are(not(presentInClassSuppressionList)))
            .should()
            .beAnnotatedWith(StatelessCheck.class);

        final ArchRule testClassesWithoutImmutableFieldsAreStateful = classes()
            .that(doNot(have(immutableFields)))
            .and()
            .haveSimpleNameEndingWith("Check")
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .and(are(not(presentInClassSuppressionList)))
            .should()
            .beAnnotatedWith(FileStatefulCheck.class)
            .orShould()
            .beAnnotatedWith(GlobalStatefulCheck.class);

        testFieldsInStatlessCheckAreImmutable.check(importedClasses);
        testClassesWithImmutableFieldsAreStateless.check(importedClasses);
        testClassesWithoutImmutableFieldsAreStateful.check(importedClasses);
    }

    private static final class ImmutableFieldArchCondition extends ArchCondition<JavaField> {
        private ImmutableFieldArchCondition() {
            super("be among immutable types");
        }

        private static boolean isMutable(JavaField javaField) {
            final JavaClass rawType = javaField.getRawType();
            boolean isMutable = false;
            if (!rawType.isEnum()) {
                final String rawTypeName = rawType.getName();
                if (!PRIMITIVE_TYPES.contains(rawTypeName)
                    && !IMMUTABLE_TYPES.contains(rawTypeName)) {
                    final JavaType javaType = javaField.getType();
                    isMutable = true;
                    if (javaType instanceof JavaParameterizedType) {
                        final JavaParameterizedType parameterizedType =
                            (JavaParameterizedType) javaType;
                        isMutable = !parameterizedType.getActualTypeArguments().stream()
                            .allMatch(actualTypeArgument -> {
                                return IMMUTABLE_TYPES.contains(
                                    actualTypeArgument.toErasure().getName());
                            });
                    }
                }
            }
            return isMutable;
        }

        @Override
        public void check(JavaField item, ConditionEvents events) {
            if (isMutable(item)) {
                final String message = String
                    .format(Locale.ROOT, "Field <%s> should %s in %s",
                            item.getFullName(), getDescription(),
                            item.getSourceCodeLocation().toString());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }
    }

    private static final class ModulePropertyPredicate extends DescribedPredicate<JavaField> {

        private ModulePropertyPredicate() {
            super("module properties");
        }

        private static boolean isModuleProperty(JavaField javaFiled) {
            boolean result = false;
            final JavaClass containingClass = javaFiled.getOwner();
            final ModuleDetails moduleDetails = MODULE_DETAILS_MAP.get(
                containingClass.getFullName());
            if (moduleDetails != null) {
                final List<ModulePropertyDetails> properties = moduleDetails.getProperties();
                result = properties.stream()
                    .map(ModulePropertyDetails::getName)
                    .anyMatch(moduleName -> moduleName.equals(javaFiled.getName()));
            }
            return result;
        }

        @Override
        public boolean apply(JavaField input) {
            return isModuleProperty(input);
        }
    }
}
