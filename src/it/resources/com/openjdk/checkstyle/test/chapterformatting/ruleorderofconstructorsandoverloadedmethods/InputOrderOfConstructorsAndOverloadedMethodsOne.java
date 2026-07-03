package com.openjdk.checkstyle.test.chapterformatting.ruleorderofconstructorsandoverloadedmethods;

public class InputOrderOfConstructorsAndOverloadedMethodsOne {
    int x;

    InputOrderOfConstructorsAndOverloadedMethodsOne() {}

    InputOrderOfConstructorsAndOverloadedMethodsOne(String s, int x, int y) {}

    // comments between constructors are allowed.
    InputOrderOfConstructorsAndOverloadedMethodsOne(int x) {}
    // violation above 'Constructors should be ordered by increasing parameter count.'

    int a = 0; // violation, Field declaration is in wrong order

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '11'."""
    InputOrderOfConstructorsAndOverloadedMethodsOne(String s, int x) {}
    // violation above 'Constructors should be ordered by increasing parameter count.'

    private enum ExampleEnum {

        ONE, TWO, THREE;

        ExampleEnum() {}

        void foo() {}

        // violation 2 lines below """Constructors should be grouped together.
        // The last grouped constructor is declared at line '25'."""
        ExampleEnum(int x, int y) {}
        // violation above 'Constructor definition in wrong order.'

        // violation 2 lines below """Constructors should be grouped together.
        // The last grouped constructor is declared at line '25'."""
        ExampleEnum(String s, int x) {}
        // violation above 'Constructor definition in wrong order.'
    }

    class InputWithOrderedCtors {
        InputWithOrderedCtors() {}
        InputWithOrderedCtors(String s) {}
        InputWithOrderedCtors(int x) {}
        InputWithOrderedCtors(String s, int x) {}
    }
}
