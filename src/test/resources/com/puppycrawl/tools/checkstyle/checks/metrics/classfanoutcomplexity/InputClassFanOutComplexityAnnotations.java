package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/* This input file is intended to be used on strict configuration: max=0 */
public class InputClassFanOutComplexityAnnotations { // violation

    private int tokenType = TokenTypes.EXPR;

    public void foo1(@TypeAnnotation char a) {}

    public void foo2(final char @TypeAnnotation [] a) {}

    @MethodAnnotation
    public void foo3() {}

    @Override
    public String toString() {
        return super.toString();
    }

    @MyAnnotation // violation
    public class InnerClass {

        @MyAnnotation
        @MethodAnnotation
        public void innerClassMethod() {}

    }

    public class InnerClass2 { // violation

        @MethodAnnotation
        @MyAnnotation
        public String innerClass2Method(@TypeAnnotation String parameter) {
            return parameter.trim();
        }

    }

    public class InnerClass3 { // violation

        @TypeAnnotation
        private final String warningsType = "boxing";

        @MyAnnotation
        @SuppressWarnings(value = warningsType)
        public String innerClass3Method() {
            return new Integer(5).toString();
        }

    }

}

class OuterClass { // violation

    private static final String name = "1";

    private static final String value = "4";

    @TwoParametersAnnotation(value = "4", tokenType = 1)
    public static final String EMPTY_STRING = "";

    @TwoParametersAnnotation(value = value, tokenType = TokenTypes.ANNOTATION)
    public static final String TAB = "\t";

}

@Target(ElementType.TYPE_USE)
@interface TypeAnnotation {}

@Target(ElementType.METHOD)
@interface MethodAnnotation {}

@MyAnnotation // violation
class MyClass {}

@MyAnnotation // violation
interface MyInterface {}

@interface MyAnnotation {}

@interface TwoParametersAnnotation {

    String value();

    int tokenType();

}
