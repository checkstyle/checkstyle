package com.puppycrawl.tools.checkstyle.utils.scopeutil;

/*
 * Config: default
 */
public interface InputScopeUtilNestedType { // ok
    class ClassWithPublicScope {
        public void test() { // violation
        }
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // Scope = private
            }
        }
    }
}
