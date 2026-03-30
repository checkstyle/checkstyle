/*
RightCurlyAloneOrEmpty
tokens = CLASS_DEF, METHOD_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyAbstract {
    abstract class Demo {
        abstract void m();
    }

    interface Demo1 {
        void m();

        default void m1() {}

        default void m2() {
            int x = 1; } // violation '}' at column 24 should be alone on a line'

    }
}
