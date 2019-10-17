/*
 * Config:
 * option = same
 * tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySameBlocksWithSemi {

    public void testMethod() {};

    public void testMethod1() {
    }; //violation

    public class TestClass {};

    public class TestClass1 {
    }; //violation

    public class TestClass2 {
        public TestClass2() {};

        public TestClass2(String someValue) {
        }; //violation
    }

    public void testMethod11() {
    }
    ;
}
