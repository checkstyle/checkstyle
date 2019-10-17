/*
 * Config:
 * option = alone
 * tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAloneBlocksWithSemi {

    public void testMethod() {}; //violation

    public void testMethod1() {
    }; //violation

    public class TestClass {}; //violation

    public class TestClass1 {
    }; //violation

    public class TestClass2 {
        public TestClass2() {}; //violation

        public TestClass2(String someValue) {
        }; //violation
    }

    public void testMethod11() {
    }
    ;

}
