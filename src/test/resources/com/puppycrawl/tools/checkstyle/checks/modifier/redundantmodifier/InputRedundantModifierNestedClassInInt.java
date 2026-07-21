/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierNestedClassInInt {
    interface PublicInnerInterface {
        interface PublicInnerInnerInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // ok in public class
            }
        }
    }
    class PublicClassInsideInt {
        private interface PrivateNestedInt {
            class ClassInPrivateNestedInt {
                // violation below 'Redundant 'public' modifier.'
                public ClassInPrivateNestedInt() { }
            }
            // violation below 'Redundant 'public' modifier.'
            public interface PrivateNestedIntWithPublicModifier {
                class ClassInPrivateNestedInt {
                    // violation below 'Redundant 'public' modifier.'
                    public ClassInPrivateNestedInt() { }
                }
            }
        }
        public interface PublicInnerInnerPublicInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // ok in public class
            }
        }
        protected interface PublicInnerInnerProtectedInterface {
          class PublicInnerClassInNestedProtectedInt {
           // violation below 'Redundant 'public' modifier.'
           public PublicInnerClassInNestedProtectedInt() { }
          }
        }
    }
    class PublicNestedClassInInterfaceWithPublicConst {
        public PublicNestedClassInInterfaceWithPublicConst() { } // ok in public class
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                // violation below 'Redundant 'public' modifier.'
                public PublicInPrivateClass() { }
            }
        }
    }
    final class FinalNestedClassInInterface {
        interface InnerInterface {
            final class FinalNestedClassInNestedInterface {}
        }
    }
}
