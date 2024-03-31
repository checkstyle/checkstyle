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

        public MyRecord1 {}
        // violation above 'Constructors should be grouped together.*'

        public MyRecord1(int a, int b, int c, int d) {
            // violation above 'Constructors should be grouped together.*'
            this(a+b, c+d);
        }

        public MyRecord1(int x, int y, int z) {
            // violation above 'Constructors should be grouped together.*'
            this(x+y, z);
        }
    }

    class MyClass {
        int x = 20;

        MyClass() {}

        MyClass(String s) {}

        String[] str;

        String[] str2;

        MyClass(int a) {}
        // violation above 'Constructors should be grouped together.*'
    }

    public record MyRecord2(double d) {
        public MyRecord2(double a, double b, double c) {
            this(a+b+c);
        }

        public MyRecord2 {}

        public MyRecord2(double a, double b) {
            this(a+b);
        }
    }

    public record MyRecord3(float f) {
        public MyRecord3(float a, float b, float c) {
            this(a+b+c);
        }
    }

    public record MyRecord4(String str) {
        public MyRecord4 {}
    }

    public record MyRecord5(long l) {
        void test() {}

        void test2() {}

        void test3() {}
    }

    public record MyRecord6(String str, int x) {}
}
