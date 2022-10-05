/*
AnnotationUseStyle
elementStyle = (default)compact_no_array
closingParens = (default)never
trailingArrayComma = ignore


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
//this file compiles in eclipse 3.4 but not with Sun's JDK 1.6.0.11

public class InputAnnotationUseStyleWithTrailingCommaIgnore
{
    @SuppressWarnings({"common",}) // violation 'Annotation style must be 'COMPACT_NO_ARRAY''
    public void foo() {


        @SuppressWarnings({"common","foo",})
        Object o = new Object() {

            @SuppressWarnings(value= // violation 'Annotation style must be 'COMPACT_NO_ARRAY''
                     {"common",})
            public String toString() {

                @SuppressWarnings(value={"leo","herbie",})
                final String pooches = "leo.herbie";

                return pooches;
            }
        };
    }

    @Test3(value={"foo",}, // violation 'Annotation style must be 'COMPACT_NO_ARRAY''
            more={"bar",})
    /**

    */
    enum P {

        @Pooches3(tokens= // violation 'Annotation style must be 'COMPACT_NO_ARRAY''
                {Pooches3.class,},other={1,})
        L,

        /**

        */
        Y;
    }

}

@interface Test3 {
    String[] value();
    String[] more() default {};
}

@interface Pooches3 {

    Class<?>[] tokens();
    int[] other();
}
