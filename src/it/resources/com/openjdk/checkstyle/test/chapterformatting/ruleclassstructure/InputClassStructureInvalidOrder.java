package com.openjdk.checkstyle.test.chapterformatting.ruleclassstructure;

// violation first line 'Header mismatch*'

public class InputClassStructureInvalidOrder {

    public int b;
    static int a = 1;
    private int x;

    InputClassStructureInvalidOrder() {
    }

    public int c;  // violation, 'Field declaration is in wrong order'
    static Long s; // violation, 'Field declaration is in wrong order'

    void foo() {}

    InputClassStructureInvalidOrder(int z) {
      // 2 violations above:
    } // 'Constructors should be grouped together.'
      // 'Constructor definition in wrong order.'

    void foo1() {}

    void bar() {}
}
