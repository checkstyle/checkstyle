/*
ConstructorsDeclarationGrouping


*/

package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

public class InputConstructorsDeclarationGrouping {

    int a;

    int b;

    void foo() {}

    InputConstructorsDeclarationGrouping() {}

    InputConstructorsDeclarationGrouping(String a) {}

    void foo2() {}

    InputConstructorsDeclarationGrouping(int a) {} // warn

    int abc;

    InputConstructorsDeclarationGrouping(double x) {} // warn

    private enum InnerEnum1 {

        one;

        int x;

        InnerEnum1() {}

        InnerEnum1(String f) {}

        String str;

        String str2;

        InnerEnum1(int x) {} // warn

        private abstract class Inner {
            Inner() {}

            int x;

            String neko;

            Inner(String g) {} // warn
        }

        InnerEnum1(double d) {} // warn
    }

    InputConstructorsDeclarationGrouping(float x) {} // warn

    InputConstructorsDeclarationGrouping(long l) {} // warn

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

      Inner2(int x) {} // warn

      String xx;

      Inner2(double d) {} // warn

      Inner2(float f) {} // warn
    }

    InputConstructorsDeclarationGrouping(long l, double d) {} // warn

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
