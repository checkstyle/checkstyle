package com.openjdk.checkstyle.test.chapterformatting.ruleorderofconstructorsandoverloadedmethods;

public class InputOrderOfConstructorsAndOverloadedMethodsTwo {

    void same(int i) {}

    void same(String s, int i, int k) {}
    // comments between overloaded methods are allowed.

    void same(String s) {}
    // violation above, 'Overloaded methods should be ordered by increasing parameter count.'

    void same(int i, String s) {}
    // violation above, 'Overloaded methods should be ordered by increasing parameter count.'

    void notSame() {}

    interface notSame{}

    void otherSame(String s) {}

    void foo() {}

    // violation 2 lines below """All overloaded methods should be placed next
    //  to each other. Previous overloaded method located at line '20'."""
    void otherSame(String s, int i) {}

    public enum ExampleEnum {
        VALUE_ONE,
        VALUE_TWO;

        public void example() {}

        public void example(String s, int i) {}

        void foo() {}

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '34'."""
        public void example(String s) {}
        // violation above, 'Overloaded methods should be ordered by increasing parameter count.'
    }
}
