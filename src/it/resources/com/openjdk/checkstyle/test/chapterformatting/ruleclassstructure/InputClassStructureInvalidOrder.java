package com.openjdk.checkstyle.test.chapterformatting.ruleclassstructure;

public class InputClassStructureInvalidOrder {

    public int b;
    static int a = 1;
    private int x;

    public InputClassStructureValidOrder() {
    }

    public int c; // violation, Field declaration is in wrong order
    static Long s; // violation, Field declaration is in wrong order

    void foo() {}

    public InputClassStructureValidOrder(int x) {
    } // violation above, 'Constructor definition in wrong order.'

    void foo1() {}

    void bar() {}
}
