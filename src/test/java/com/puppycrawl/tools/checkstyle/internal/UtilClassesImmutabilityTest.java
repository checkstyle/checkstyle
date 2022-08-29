package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.util.Set;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaParameterizedType;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(packages = {
    "com.puppycrawl.tools.checkstyle.utils",
    "com.puppycrawl.tools.checkstyle.checks.javadoc.utils"
}, importOptions = ImportOption.DoNotIncludeTests.class)
public class UtilClassesImmutabilityTest {

    /**
     * Default immutable types canonical names.
     */
    private static final Set<String> DEFAULT_IMMUTABLE_TYPES = Set.of(
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
     * Thread safe primitive types. long and double aren't thread safe.
     */
    private static final Set<String> PRIMITIVE_TYPES = Set.of(
        "byte",
        "short",
        "int",
        "float",
        "char",
        "boolean"
    );

    /**
     * List of suppressed fields.
     */
    private static final Set<String> SUPPRESSED_FIELDS = Set.of(
        "com.puppycrawl.tools.checkstyle.checks.javadoc.utils.TagInfo.position",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_BIT_SET",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_BYTE_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_DOUBLE_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_INTEGER_OBJECT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_INT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_OBJECT_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY",
        "com.puppycrawl.tools.checkstyle.utils.JavadocUtil$JavadocTagType.$VALUES",
        "com.puppycrawl.tools.checkstyle.utils.JavadocUtil$JavadocTagType.ALL",
        "com.puppycrawl.tools.checkstyle.utils.JavadocUtil$JavadocTagType.BLOCK",
        "com.puppycrawl.tools.checkstyle.utils.JavadocUtil$JavadocTagType.INLINE",
        "com.puppycrawl.tools.checkstyle.utils.TokenUtil.TOKEN_IDS",
        "com.puppycrawl.tools.checkstyle.utils.XpathUtil.TOKEN_TYPES_WITH_TEXT_ATTRIBUTE"
    );

    /**
     * Descrie predicate filtering suppressed fields.
     */
    private static final DescribedPredicate<JavaField> ARE_NOT_SUPPRESSED =
        new DescribedPredicate<>("are not suppressed") {
        @Override
        public boolean apply(JavaField input) {
            return !SUPPRESSED_FIELDS.contains(input.getFullName());
        }
    };

    /**
     * ArchCondition for immutable fields.
     */
    private static final ArchCondition<JavaField> BE_IMMUTABLE = new ArchCondition<>(
        "be among default immutable types") {
        @Override
        public void check(JavaField item, ConditionEvents events) {
            final String rawTypeName = item.getRawType().getName();
            boolean addViolation = false;
            if (!(PRIMITIVE_TYPES.contains(rawTypeName)
                || DEFAULT_IMMUTABLE_TYPES.contains(rawTypeName))) {
                final JavaType javaType = item.getType();
                addViolation = true;
                if (javaType instanceof JavaParameterizedType) {
                    final JavaParameterizedType parameterizedType =
                        (JavaParameterizedType) javaType;
                    addViolation = !parameterizedType.getActualTypeArguments().stream()
                        .allMatch(actualTypeArgument -> {
                            return DEFAULT_IMMUTABLE_TYPES.contains(
                                actualTypeArgument.toErasure().getName());
                        });
                }
            }

            if (addViolation) {
                final String message = String.format(
                    "Field <%s> should %s in %s", item.getFullName(), getDescription(),
                    item.getSourceCodeLocation().toString());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }
    };

    /**
     * ArchRule for testing immutability.
     */
    @ArchTest
    public static final ArchRule TEST_IMMUTABILITY =
        fields()
            .that(ARE_NOT_SUPPRESSED)
            .and()
            .areDeclaredInClassesThat()
            .haveSimpleNameEndingWith("Util")
            .should(BE_IMMUTABLE)
            .andShould()
            .beFinal()
            .andShould()
            .beStatic();

}
