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

        public MyRecord1 {} // violation

        public MyRecord1(int x, int y, int z) { // violation
            this(x+y, z);
        }
    }

    class MyClass {
        int x = 20;

        MyClass() {}

        MyClass(String s) {}

        String[] str;

        MyClass(int a) {} // violation
    }
}
