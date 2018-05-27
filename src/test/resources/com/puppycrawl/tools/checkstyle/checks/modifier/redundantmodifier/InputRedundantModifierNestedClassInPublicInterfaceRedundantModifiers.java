////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
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
                public ClassInPrivateNestedInterface() { } // Redundant in not public class
            }
            public interface PrivateNestedInterfaceWithPublicModifier {
                class ClassInPrivateNestedInterface {
                    public ClassInPrivateNestedInterface() { } // Redundant in non public scope
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
           public PublicInnerClassInNestedProtectedInterfaces() { } // Redundant in non public scope
          }
        }
    }
    class PublicNestedClassInInterfaceWithPublicConstructor {
        public PublicNestedClassInInterfaceWithPublicConstructor() { } // OK in public class
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // Redundant in non public class
            }
        }
    }
    final class FinalNestedClassInInterface {
        interface InnerInterface {
            final class FinalNestedClassInNestedInterface {}
        }
    }
}
