/*
ConstructorsDeclarationGrouping


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingInner {

    InputConstructorsDeclarationGroupingInner() {}

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

    InputConstructorsDeclarationGroupingInner(long l, double d) {}
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
