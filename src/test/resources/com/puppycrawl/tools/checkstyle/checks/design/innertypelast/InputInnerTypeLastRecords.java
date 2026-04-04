/*
InnerTypeLast


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastRecords {
    record Test() {
        private static String s;

        record InnerTest1() {
        }

        public void test() { // violation 'Init blocks, constructors, fields and methods should be before inner types.'
        }
    }

    public void test() { // violation 'Init blocks, constructors, fields and methods should be before inner types.'
    }

    record Test3() {
        private static String s;

        class InnerTest1 {
        }

        public void test() { // violation 'Init blocks, constructors, fields and methods should be before inner types.'
        }
    }

    record Test4() {
        private static String s;

        record MyInnerRecord() {
            void foo() {}
            class InnerInnerClass{}
            public MyInnerRecord{} // violation 'Init blocks, constructors, fields and methods should be before inner types.'
        }

        class MyInnerClass {
            void foo (){}
            class InnerInnerClass{}
            public MyInnerClass(){} // violation 'Init blocks, constructors, fields and methods should be before inner types.'

        }

        static Test3 innerRecord = new Test3(); // violation 'Init blocks, constructors, fields and methods should be before inner types.'

        public void test() { // violation 'Init blocks, constructors, fields and methods should be before inner types.'
        }
    }

    record Test5() {
        private static String s;
        static Test3 myRecordTest = new Test3();

        public void test() {
        }
    }
}
