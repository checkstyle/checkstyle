package com.puppycrawl.tools.checkstyle.utils.scopeutil;

/*
 * Config: default
 */
public interface InputScopeUtilNestedType { // ok
    class ClassWithPublicScope {
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // Scope = private
            }
        }
    }
}
