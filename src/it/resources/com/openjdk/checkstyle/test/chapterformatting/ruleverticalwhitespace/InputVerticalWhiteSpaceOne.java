package com.openjdk.checkstyle.test.chapterformatting.ruleverticalwhitespace;

// violation first line 'Header mismatch'

import java.util.List;
public class InputVerticalWhiteSpaceOne {
    // violation above ''CLASS_DEF' should be separated from previous line'
    int var1 = 10;
    List<Integer> list; // violation below ''CTOR_DEF' should be separated from previous line'
    InputVerticalWhiteSpaceOne() {}
    InputVerticalWhiteSpaceOne(int var2) {}
    // violation above ''CTOR_DEF' should be separated from previous line'

    void method() {}
    void method1() {} // violation ''METHOD_DEF' should be separated from previous line'
    { // violation ''INSTANCE_INIT' should be separated from previous line'
        int a = 0;
    }
    static { // violation ''STATIC_INIT' should be separated from previous line'
    }

    static {
    }

}
