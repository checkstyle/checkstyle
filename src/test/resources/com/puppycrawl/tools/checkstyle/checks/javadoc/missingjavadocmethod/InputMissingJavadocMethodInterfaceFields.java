package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/**
 * Config:
 * scope = public
 */
public interface InputMissingJavadocMethodInterfaceFields {
    class NoModifierClass {
        public void test() { // violation
        }
        protected void testTwo() {} // ok
        private void testThree() {} // ok
    }

    static void foo1() {} // violation
    public static void foo2() {} // violation
}
