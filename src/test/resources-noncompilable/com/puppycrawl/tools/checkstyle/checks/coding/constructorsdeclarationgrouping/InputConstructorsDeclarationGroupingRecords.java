/*
ConstructorsDeclarationGrouping


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingRecords {
    public record MyRecord1(int x, int y) {
        public MyRecord1(int a) {
            this(a,a);
        }

        void foo() {}

        void foo2() {}

        public MyRecord1 {} // violation

        public MyRecord1(int a, int b, int c, int d) { // violation
            this(a+b, c+d);
        }

        public MyRecord1(int x, int y, int z) { // violation
            this(x+y, z);
        }
    }

    class MyClass {
        int x = 20;

        MyClass() {}

        MyClass(String s) {}

        String[] str;

        String[] str2;

        MyClass(int a) {} // violation
    }

    public record MyRecord4(double d) {
        public MyRecord4(double d) {
            this(d);
        }

        public MyRecord4 {}

        public MyRecord4(double a, double b) {
            this(a+b);
        }
    }
}
