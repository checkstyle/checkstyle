package com.puppycrawl.tools.checkstyle.utils.scopeutil;

/*
 * Config: default
 */
public interface InputScopeUtilNestedType { // ok
    class ClassWithPublicScope {
        public void test() { // Scope = public
        }
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // Scope = private
            }
        }
        public interface PublicInterface {
            class implicitPublicCLass {
                public void method4 () {
                }
            }
        }
    }
}
