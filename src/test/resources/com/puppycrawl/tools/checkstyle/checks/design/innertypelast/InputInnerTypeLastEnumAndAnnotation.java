/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastEnumAndAnnotation {

    enum Status {
        ACTIVE, INACTIVE
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    private int count;

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    public void doWork() {
    }
}

class WithInnerAnnotation {

    @interface MyAnnotation {
        String value() default "";
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    private int field;

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    public void method() {
    }
}

class WithInnerEnumCtorsAndInits {

    enum Status {
        ACTIVE, INACTIVE
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    {
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    static {
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    public WithInnerEnumCtorsAndInits() {
    }
}

enum EnumWithInnerEnum {

    VAL;

    enum InnerEnum {
        X
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    private int a;
}

class NoViolationEnumLast {

    private int field;

    public void method() {
    }

    enum Status {
        ACTIVE, INACTIVE
    }
}

class NoViolationAnnotationLast {

    private int field;

    public void method() {
    }

    @interface MyAnnotation {
        String value() default "";
    }
}
