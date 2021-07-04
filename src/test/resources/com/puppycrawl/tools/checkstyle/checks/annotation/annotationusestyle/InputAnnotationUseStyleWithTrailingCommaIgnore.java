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
    @SuppressWarnings({"common",}) // violation
    public void foo() {


        @SuppressWarnings({"common","foo",})
        Object o = new Object() {

            @SuppressWarnings(value={"common",}) // violation
            public String toString() {

                @SuppressWarnings(value={"leo","herbie",})
                final String pooches = "leo.herbie";

                return pooches;
            }
        };
    }

    @Test3(value={"foo",}, more={"bar",}) // violation
    /**

    */
    enum P {

        @Pooches3(tokens={Pooches3.class,},other={1,}) // violation
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
