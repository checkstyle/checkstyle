/*
ParameterNumber
max = 2
ignoreAnnotatedBy = MyAnno, Session
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF,CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

public class InputParameterNumberIgnoreAnnotatedBy {
    @MyAnno
    InputParameterNumberIgnoreAnnotatedBy(int a, int b, int c) {}

    void method1(int a, int b) {}

    @MyAnno
    void method2(int a, int b, int c) {}

    @Session(uid = "Y2K")
    void method3(int a, int b, int c, int d) {}

    @Wawa
    void method5(int a, int b, int c) { // violation, 'More than 2 parameters (found 3).'
    }

    @Wawa
    void method6(int a, int b) {}


    void method7(int a, int b, int c, int d) { // violation, 'More than 2 parameters (found 4).'
    }

    class InnerClass extends InputParameterNumberIgnoreAnnotatedBy {
        @Override
        void method2(int a, int b, int c) { // violation, 'More than 2 parameters (found 3).'
            int d = a + c;
        }

        InnerClass(int a, int b) {
            super(1, 2, 3);
        }

        InnerClass(int a, int b, int c, int d) { // violation, 'More than 2 parameters (found 4).'
            super(1, 2, 3);
        }
    }

    static class Very {
        static class Deep {
            static class Rabbit {
                enum Colors {
                    GOLDEN(1, 2, 3) {
                        void jump(int a, int b) {}
                        @MyAnno
                        void roll(int a, int b, int c) {}

                        // violation below, 'More than 2 parameters (found 3).'
                        void loaf(int a, int b, int c) {}
                    };

                    @Wawa
                    private Colors(@MyAnno int a, int b, int c) {}
                    // violation above, 'More than 2 parameters (found 3).'

                    @Session(uid = ":D")
                    private Colors(@Wawa int a, @Wawa int b, @Wawa int c, @Wawa int d) {}

                    private Colors(int a) {}
                }
                static class Hole {
                    static {
                        new Object() {
                            @MyAnno @Session(uid = "G0F")
                            void method1(int a, int b, int c) {}

                            // violation below, 'More than 2 parameters (found 4).'
                            void method3(@MyAnno int a, @MyAnno int b, @MyAnno int c, int d) {}
                        };
                    }
                }
            }
        }
    }

    @Wawa
    @MyAnno
    void method8(int a, int b, int c) {
    }

    @Session(uid = "H1") @Bit
    @Wawa
    void method9(int a, int b, int c) {
    }

    @Bit
    @Wawa
    void method10(int a, int b, int c) { // violation, 'More than 2 parameters (found 3).'
    }

    interface Reader {
        void method1 (int a, int b);

        @MyAnno
        void method2 (int a, int b, int c);

        void method3 (int a, int b, int c); // violation, 'More than 2 parameters (found 3).'
    }

    @interface Wawa {}
    @interface Bit {}
    @interface MyAnno {}
    @interface Session {
        String uid();
    }
}
