/*
ParameterNumber
max = 2
ignoreAnnotatedBy = MyAnno
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.parameternumber;

public class InputXpathParameterNumberIgnoreAnnotatedBy {
    static class InnerClass {
        static {
            new Object() {
                void method() {
                    if (true) {
                        new Object() {
                            @MyAnno
                            void ignoredMethod(int a, @MyAnno int b, int c) {

                            }

                            // violation below 'More than 2 parameters'
                            void checkedMethod(int a, @MyAnno int b, int c) {

                            }
                        };
                    }
                }
            };
        }
    }

    @interface MyAnno {}
}
