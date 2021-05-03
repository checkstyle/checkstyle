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
}
