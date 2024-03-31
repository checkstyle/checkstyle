/*
ConstructorsDeclarationGrouping


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingRecords {
    record MyRecord1(int x, int y) {
        MyRecord1(int a) {
            this(a,a);
        }

        void foo() {}

        MyRecord1 {} // violation

        MyRecord1(int x, int y, int z) {
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
