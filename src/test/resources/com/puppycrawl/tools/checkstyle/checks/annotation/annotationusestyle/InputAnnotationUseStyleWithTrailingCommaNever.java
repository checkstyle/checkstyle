/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = NEVER


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

    @Test4(value={"foo",}, more={"bar",}) // 2 violations
    /**

    */
    enum P {

        @Pooches4(tokens={Pooches4.class,},other={1,}) // 2 violations
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
