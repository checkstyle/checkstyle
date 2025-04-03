/*
ParameterNumber
max = 2
ignoreAnnotatedBy = java.lang.Deprecated, InnerClass.InnerAnno, Session
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF,CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

public class InputParameterNumberIgnoreAnnotatedByFullyQualifiedClassName {
    @java.lang.Deprecated
    void method3(int a, int b, int c) {}

    @Deprecated
    void method4(int a, int b, int c) {} // violation, 'More than 2 parameters (found 3).'

    void method5(int a, int b, int c) {} // violation, 'More than 2 parameters (found 3).'

    @Session
    void method6(int a, int b, int c) {}

    @InputParameterNumberIgnoreAnnotatedByFullyQualifiedClassName.Session
    void method7(int a, int b, int c) {} // violation, 'More than 2 parameters (found 3).'

    @com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber
            .InputParameterNumberIgnoreAnnotatedByFullyQualifiedClassName.Session
    void method8(int a, int b, int c) {} // violation, 'More than 2 parameters (found 3).'

    @com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber
            .InputParameterNumberIgnoreAnnotatedByFullyQualifiedClassName.Session
    void method8(int a, int b) {}

    private static class InnerClass {
        private @interface InnerAnno {}

        @InnerClass.InnerAnno
        void method1(int a, int b, int c) {
        }

        @InnerAnno
        void method2(int a, int b, int c) { // violation, 'More than 2 parameters (found 3).'
        }

        @Bit
        void method3(int a, int b, int c) { // violation, 'More than 2 parameters (found 3).'
        }

        @InnerAnno
        void method2(int a, int b) {
        }

        @Bit
        void method3(int a, int b) {
        }

        void method4(int a, int b) {
        }
    }

    @interface Session {}
    @interface Bit{}
}
