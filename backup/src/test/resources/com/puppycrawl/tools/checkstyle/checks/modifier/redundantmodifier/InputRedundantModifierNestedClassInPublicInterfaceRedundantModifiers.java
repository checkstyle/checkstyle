/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierNestedClassInPublicInterfaceRedundantModifiers {
    interface PublicInnerInterface {
        interface PublicInnerInnerInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // OK in public class
            }
        }
    }
    class PublicClassInsideInterface {
        private interface PrivateNestedInterface {
            class ClassInPrivateNestedInterface {
                public ClassInPrivateNestedInterface() { } // violation
            }
            public interface PrivateNestedInterfaceWithPublicModifier { // violation
                class ClassInPrivateNestedInterface {
                    public ClassInPrivateNestedInterface() { } // violation
                }
            }
        }
        public interface PublicInnerInnerPublicInterface {
            class PublicInnerClassInNestedPublicInterfaces {
                public PublicInnerClassInNestedPublicInterfaces() { } // OK in public class
            }
        }
        protected interface PublicInnerInnerProtectedInterface {
          class PublicInnerClassInNestedProtectedInterfaces {
           public PublicInnerClassInNestedProtectedInterfaces() { } // violation
          }
        }
    }
    class PublicNestedClassInInterfaceWithPublicConstructor {
        public PublicNestedClassInInterfaceWithPublicConstructor() { } // OK in public class
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
