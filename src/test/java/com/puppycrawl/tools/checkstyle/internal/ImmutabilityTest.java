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
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
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
     * List of suppressed fields.
     */
    private static final Set<String> SUPPRESSED_FIELDS = Set.of(
        "com.puppycrawl.tools.checkstyle.utils.TokenUtil.TOKEN_IDS",
        "com.puppycrawl.tools.checkstyle.utils.XpathUtil.TOKEN_TYPES_WITH_TEXT_ATTRIBUTE"
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
     * ArchCondition for immutable fields.
     */
    private static final ArchCondition<JavaField> BE_IMMUTABLE = new JavaFieldArchCondition();

    /**
     * DescribedPredicate filtering suppressed fields and zero size arrays as they are immutable.
     */
    private static final DescribedPredicate<JavaField> SUPPRESSED =
        new DescribedPredicate<>("suppressed") {
            @Override
            public boolean apply(JavaField input) {
                return SUPPRESSED_FIELDS.contains(input.getFullName());
            }
        };

    /**
     * DescribedPredicate filtering zero size arrays as they are immutable by definition.
     */
    private static final DescribedPredicate<JavaField> ZERO_SIZE_ARRAYS =
        new DescribedPredicate<>("zero size arrays") {
            @Override
            public boolean apply(JavaField input) {
                return ZERO_SIZE_ARRAY_FIELDS.contains(input.getFullName());
            }
        };

    /**
     * Test to ensure that fields in util classes are immutable.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void testImmutability() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.utils",
                            "com.puppycrawl.tools.checkstyle.checks.javadoc.utils");
        final ArchRule testImmutability = fields()
            .that(are(not(SUPPRESSED)).and(are(not(ZERO_SIZE_ARRAYS))))
            .and()
            .areDeclaredInClassesThat()
            .haveSimpleNameEndingWith("Util")
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal()
            .andShould()
            .beStatic();

        testImmutability.check(importedClasses);
    }

    private static final class JavaFieldArchCondition extends ArchCondition<JavaField> {
        private JavaFieldArchCondition() {
            super("be among immutable types");
        }

        @Override
        public void check(JavaField item, ConditionEvents events) {
            final JavaClass rawType = item.getRawType();
            boolean addViolation = false;
            if (!rawType.isEnum()) {
                final String rawTypeName = rawType.getName();
                if (!PRIMITIVE_TYPES.contains(rawTypeName)
                    && !IMMUTABLE_TYPES.contains(rawTypeName)) {
                    final JavaType javaType = item.getType();
                    addViolation = true;
                    if (javaType instanceof JavaParameterizedType) {
                        final JavaParameterizedType parameterizedType =
                            (JavaParameterizedType) javaType;
                        addViolation = !parameterizedType.getActualTypeArguments().stream()
                            .allMatch(actualTypeArgument -> {
                                return IMMUTABLE_TYPES.contains(
                                    actualTypeArgument.toErasure().getName());
                            });
                    }
                }
            }

            if (addViolation) {
                final String message = String
                    .format(Locale.ROOT, "Field <%s> should %s in %s",
                            item.getFullName(), getDescription(),
                            item.getSourceCodeLocation().toString());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }
    }
}
