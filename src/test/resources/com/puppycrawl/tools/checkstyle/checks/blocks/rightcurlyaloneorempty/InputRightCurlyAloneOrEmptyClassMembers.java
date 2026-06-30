/*
RightCurlyAloneOrEmpty
tokens = METHOD_DEF, STATIC_INIT, INSTANCE_INIT, COMPACT_CTOR_DEF, CTOR_DEF
allowMultiBlock = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyClassMembers {

    static {
    }

    static { } int a;
    // violation above ''}' at column 14 should be alone on a line'

    static {
    }    // comment
    // violation above ''}' at column 5 should be alone on a line'


    { int x = 0;
    } // comment
    // violation above ''}' at column 5 should be alone on a line'

    InputRightCurlyAloneOrEmptyClassMembers() {
    }

    InputRightCurlyAloneOrEmptyClassMembers(int x) {
    } int k;
    // violation above ''}' at column 5 should be alone on a line'

    void method() {
    } void
    method2() { }
    // violation 2 lines above ''}' at column 5 should be alone on a line'

    void method3() {
    int t = 1; }
    // violation above ''}' at column 16 should be alone on a line'

    void method4() {
        // comment
    }

    record Point(int x, int y) {
        Point { int c = x;
            int b = y;}
        // violation above ''}' at column 23 should be alone on a line'
    }

    record Point2(int x, int y) {
        Point2 { int c = x;
        } // comment
        // violation above ''}' at column 9 should be alone on a line'
    }

    // violation 2 lines below ''}' at column 5 should be alone on a line'
    void method5() {
    } // comment
    void method6() {}

    // violation 2 lines below ''}' at column 5 should be alone on a line'
    void method7() {
    } /* comment */
    void method8() {}
}
