/*
InnerTypeLast


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastRecords {
    record Test() {
        private static String s; // ok

        record InnerTest1() { // ok
        }

        public void test() { // violation
        }
    }

    public void test() { // violation
    }

    record Test3() {
        private static String s; // ok

        class InnerTest1 {
        }

        public void test() { // violation
        }
    }

    record Test4() {
        private static String s; // ok

        record MyInnerRecord() {
            void foo() {}
            class InnerInnerClass{}
            public MyInnerRecord{} // violation
        }

        class MyInnerClass {
            void foo (){}
            class InnerInnerClass{}
            public MyInnerClass(){} // violation

        }

        static Test3 innerRecord = new Test3(); // violation

        public void test() { // violation
        }
    }

    record Test5() {
        private static String s; // ok
        static Test3 myRecordTest = new Test3(); //ok

        public void test() {
        } // ok
    }
}
