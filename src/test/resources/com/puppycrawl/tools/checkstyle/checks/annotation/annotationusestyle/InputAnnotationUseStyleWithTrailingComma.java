package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
//this file compiles in eclipse 3.4 but not with Sun's JDK 1.6.0.11

/**

*/
public class InputAnnotationUseStyleWithTrailingComma
{
    @SuppressWarnings({"common",})
    public void foo() {


        @SuppressWarnings({"common","foo",})
        Object o = new Object() {

            @SuppressWarnings(value={"common",})
            public String toString() {

                @SuppressWarnings(value={"leo","herbie",})
                final String pooches = "leo.herbie";

                return pooches;
            }
        };
    }

    @Test(value={"foo",}, more={"bar",})
    /**

    */
    enum P {

        @Pooches(tokens={Pooches.class,},other={1,})
        L,

        /**

        */
        Y;
    }

}

@interface Test {
    String[] value();
    String[] more() default {};
    /**

    */
}

@interface Pooches {

    Class<?>[] tokens();
    int[] other();
}
