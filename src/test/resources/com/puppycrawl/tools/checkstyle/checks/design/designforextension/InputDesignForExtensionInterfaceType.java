package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

/**
 * Config:
 * requiredJavadocPhrase = This implementation
 */
public interface InputDesignForExtensionInterfaceType {
    static class A  {
        @Deprecated // violation
        public boolean foo1(){ return true; }

        @Deprecated // violation
        protected boolean foo2(){ return true; }

        @Deprecated // ok
        private boolean foo3(){ return true; }
    }

    class B  {
        @Deprecated // violation
        public boolean foo4(){ return true; }

        @Deprecated // violation
        protected boolean foo5(){ return true; }

        @Deprecated // ok
        private boolean foo6(){ return true; }
    }

    static class C {
        public boolean foo7(){ return true; } // violation

        protected boolean foo8(){ return true; } // violation

        private boolean foo9(){ return true; } // ok
    }

    public default boolean methodWithLocalClass(String input) {
        class LocalClass {

        public boolean test() { // violation
            return false;
            }
        }
        return true;
    }

}
