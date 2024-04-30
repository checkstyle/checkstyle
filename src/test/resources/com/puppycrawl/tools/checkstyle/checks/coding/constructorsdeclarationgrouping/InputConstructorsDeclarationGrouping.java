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

    InputConstructorsDeclarationGrouping(int a) {}
    // violation above 'Constructors should be grouped together.*'

    int abc;

    InputConstructorsDeclarationGrouping(double x) {}
    // violation above 'Constructors should be grouped together.*'

    private enum InnerEnum1 {

        one;

        int x;

        InnerEnum1() {}

        InnerEnum1(String f) {}

        String str;

        String str2;

        InnerEnum1(int x) {}
        // violation above 'Constructors should be grouped together.*'

        private abstract class Inner {
            Inner() {}

            int x;

            String neko;

            Inner(String g) {}
            // violation above 'Constructors should be grouped together.*'
        }

        InnerEnum1(double d) {}
        // violation above 'Constructors should be grouped together.*'
    }

    InputConstructorsDeclarationGrouping(float x) {}
    // violation above 'Constructors should be grouped together.*'

    InputConstructorsDeclarationGrouping(long l) {}
    // violation above 'Constructors should be grouped together.*'

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

      Inner2(int x) {}
      // violation above 'Constructors should be grouped together.*'

      String xx;

      Inner2(double d) {}
      // violation above 'Constructors should be grouped together.*'

      Inner2(float f) {}
      // violation above 'Constructors should be grouped together.*'
    }

    InputConstructorsDeclarationGrouping(long l, double d) {}
    // violation above 'Constructors should be grouped together.*'

    InputConstructorsDeclarationGrouping annoynmous = new InputConstructorsDeclarationGrouping() {
        int x;
        void test() {}
        void test2() {}
    };

    private enum InnerEnum2 {
        ONE,TWO,THREE;
        void test() {}
        void test2() {}
        void test3() {}
    }

    private enum InnerEnum3 {
        InnerEnum3() {}
    }

    private enum InnerEnum4 {}

    private class Inner3 {
        void test() {}
        void test2() {}
        void test3() {}
    }

    private class Inner4 {
        Inner4() {}
    }

    private class Inner5 {}
}
