/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierNestedClassInInt {
    interface PublicInnerInterface {
        interface PublicInnerInnerInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // OK in public class
            }
        }
    }
    class PublicClassInsideInt {
        private interface PrivateNestedInt {
            class ClassInPrivateNestedInt {
                public ClassInPrivateNestedInt() { } // violation
            }
            public interface PrivateNestedIntWithPublicModifier { // violation
                class ClassInPrivateNestedInt {
                    public ClassInPrivateNestedInt() { } // violation
                }
            }
        }
        public interface PublicInnerInnerPublicInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // OK in public class
            }
        }
        protected interface PublicInnerInnerProtectedInterface {
          class PublicInnerClassInNestedProtectedInt {
           public PublicInnerClassInNestedProtectedInt() { } // violation
          }
        }
    }
    class PublicNestedClassInInterfaceWithPublicConst {
        public PublicNestedClassInInterfaceWithPublicConst() { } // OK in public class
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // violation
            }
        }
    }
    final class FinalNestedClassInInterface {
        interface InnerInterface {
            final class FinalNestedClassInNestedInterface {}
        }
    }
}
