package com.openjdk.checkstyle.test.chapterformatting.ruleverticalwhitespace;
public class InputVerticalWhiteSpaceTwo {
    // violation first line 'Header mismatch'
    // violation 2 lines above ''CLASS_DEF' should be separated from previous line'
    int var1 = 0;
    int var2 = 0;

    int var3 = 0;
    enum Temp { // violation ''ENUM_DEF' should be separated from previous line'

    } // violation below ''RECORD_DEF' should be separated from previous line'
    record Order(int id, int param) {

        Order(int ids) {
            this(ids, 1);
        } // violation below ''CTOR_DEF' should be separated from previous line'
        Order(int id, int param) {
            this.id = id;
            this.param = param;
        }

    }
    interface Test { // violation ''INTERFACE_DEF' should be separated from previous line'
    }
}
