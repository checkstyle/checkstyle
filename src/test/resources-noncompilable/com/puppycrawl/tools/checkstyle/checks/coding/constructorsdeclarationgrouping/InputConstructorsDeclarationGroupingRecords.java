/*
ConstructorsDeclarationGrouping


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingRecords {
    public record MyRecord(int x, int y) {
        public MyRecord(int a) {
            this(a,a);
        }

        void foo() {}

        void foo2() {
            System.out.println("foo2");
        }

        public MyRecord {} // violation

        public MyRecord(int a, int b, int c, int d) { // violation
            this(a+b, c+d);
        }

        public MyRecord(int x, int y, int z) { // violation
            this(x+y, z);
        }
    }

    class MyClass {
        int x = 20;

        MyClass() {}

        MyClass(String s) {}

        String[] str;

        String[] str;

        MyClass(int a) {} // violation
    }
}
