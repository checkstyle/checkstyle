/*
ConstructorsDeclarationGrouping


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGrouping {

    int a;

    int b;

    void foo() {}

    InputConstructorsDeclarationGrouping() {}

    InputConstructorsDeclarationGrouping(String a) {}

    void foo2() {}

    InputConstructorsDeclarationGrouping(int a) {} // violation

    int abc;

    InputConstructorsDeclarationGrouping(double x) {} // violation

    private enum Testing {

        one;

        int x;

        Testing() {}

        Testing(String f) {}

        String str;

        String str2;

        Testing(int x) {} // violation

        private abstract class Inner {
            Inner() {}

            int x;

            String neko;

            Inner(String g) {} // violation
        }

        Testing(double d) {} // violation
    }

    InputConstructorsDeclarationGrouping(float x) {} // violation

    InputConstructorsDeclarationGrouping(long l) {} // violation

    private class Inner {
        Inner() {}

        Inner(String str) {}

        // Comments are allowed between constructors.
        Inner(int x) {}
    }

    private class Inner2 {
      Inner2() {}

      Inner2(String str) {}

      int x;

      Inner2(int x) {} // violation

      String xx;

      Inner2(double d) {} // violation

      Inner2(float f) {} // violation
    }

    InputConstructorsDeclarationGrouping(long l, double d) {} // violation

    InputConstructorsDeclarationGrouping annoynmous = new InputConstructorsDeclarationGrouping() {
        int x;

        void test() {}

        void test2() {}
    };
}
