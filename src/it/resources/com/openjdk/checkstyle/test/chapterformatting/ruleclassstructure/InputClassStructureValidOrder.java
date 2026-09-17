package com.openjdk.checkstyle.test.chapterformatting.ruleclassstructure;

// violation first line 'Header mismatch'

/** Input file with class structure violations. */
public class InputClassStructureValidOrder {

    public int b;
    static int a = 1;
    private int x;

    public InputClassStructureValidOrder() {
    }

    public InputClassStructureValidOrder(int z) {
    }

    void foo() {}

    void foo1() {}

    void bar() {}
}
