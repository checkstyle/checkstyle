/*
OverloadMethodsDeclarationOrder
modifierGroups = static, public, protected|package



*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderRecords2 {
    record MyRecord1() {
        public void foo(int i) { } // ok

        public void foo(String s) { } // ok

        public void notFoo() { } // ok

        // violation below 'All overloaded methods.*Previous overloaded method located at.*'
        public void foo(int i, String s) { }

        protected void foo(String s, int i) { } // ok

        void foo(long l) { } // ok

        static void notFoo(int i) { } // ok

        // violation below 'All overloaded methods.*Previous overloaded method located at.*'
        void foo(String baz, long l) { }

        // violation below 'All overloaded methods.*Previous overloaded method located at.*'
        public static void notFoo(long l) { }
    }
}
