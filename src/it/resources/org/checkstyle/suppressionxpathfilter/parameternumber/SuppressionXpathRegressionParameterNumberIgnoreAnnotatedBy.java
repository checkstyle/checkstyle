package org.checkstyle.suppressionxpathfilter.parameternumber;

public class SuppressionXpathRegressionParameterNumberIgnoreAnnotatedBy {
    static class InnerClass {
        static {
            new Object() {
                void method() {
                    if (true) {
                        new Object() {
                            @MyAnno
                            void ignoredMethod(int a, @MyAnno int b, int c) {

                            }

                            void checkedMethod(int a, @MyAnno int b, int c) { // warn

                            }
                        };
                    }
                }
            };
        }
    }

    @interface MyAnno {}
}
