/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastEnumAnnotation {

    enum Status {
        ACTIVE, INACTIVE
    }

    // violation below 'Init blocks, constructors, fields and methods should be.*'
    private int count;

    // violation below 'Init blocks, constructors, fields and methods should be.*'
    public void doWork() {
    }
}

class InputInnerTypeLastEnumAnnotation2 {

    @interface MyAnno {
        String value() default "";
    }

    // violation below 'Init blocks, constructors, fields and methods should be.*'
    private int count;

    // violation below 'Init blocks, constructors, fields and methods should be.*'
    InputInnerTypeLastEnumAnnotation2() {
    }
}

class InputInnerTypeLastEnumAnnotation3 {

    static class Nested {

        enum Level {
            LOW, HIGH
        }

        // violation below 'Init blocks, constructors, fields and methods should be.*'
        private int nestedCount;
    }
}
