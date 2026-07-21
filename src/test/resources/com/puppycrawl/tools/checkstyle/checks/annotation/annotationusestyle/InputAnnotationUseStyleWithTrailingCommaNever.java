/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = (default)NEVER


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
//this file compiles in eclipse 3.4 but not with Sun's JDK 1.6.0.11

public class InputAnnotationUseStyleWithTrailingCommaNever
{
    // violation below 'Annotation array values cannot contain trailing comma'
    @SuppressWarnings({"common",})
    public void foo() {


        // violation below 'Annotation array values cannot contain trailing comma'
        @SuppressWarnings({"common","foo",})
        Object o = new Object() {

            // violation below 'Annotation array values cannot contain trailing comma'
            @SuppressWarnings(value={"common",})
            public String toString() {

                // violation below 'Annotation array values cannot contain trailing comma'
                @SuppressWarnings(value={"leo","herbie",})
                final String pooches = "leo.herbie";

                return pooches;
            }
        };
    }
    // 2 violations 3 lines below:
    // 'Annotation array values cannot contain trailing comma.'
    // 'Annotation array values cannot contain trailing comma.'
    @Test4(value={"foo",}, more={"bar",})
    /**

    */
    enum P {
        // 2 violations 3 lines below:
        // 'Annotation array values cannot contain trailing comma.'
        // 'Annotation array values cannot contain trailing comma.'
        @Pooches4(tokens={Pooches4.class,},other={1,})
        L,

        /**

        */
        Y;
    }

}

@interface Test4 {
    String[] value();
    String[] more() default {};
}

@interface Pooches4 {

    Class<?>[] tokens();
    int[] other();
}
