package com.openjdk.checkstyle.test.chapterformatting.ruleclassstructure;

public class InputClassStructureInvalidOrder {

    public int b;
    static int a = 1;
    private int x;

    InputClassStructureInvalidOrder() {
    }

    public int c; // violation, Field declaration is in wrong order
    static Long s; // violation, Field declaration is in wrong order

    void foo() {}

    InputClassStructureInvalidOrder(int x) { // 2 violations
    } // 'Constructor definition in wrong order.'

    void foo1() {}

    void bar() {}
}
