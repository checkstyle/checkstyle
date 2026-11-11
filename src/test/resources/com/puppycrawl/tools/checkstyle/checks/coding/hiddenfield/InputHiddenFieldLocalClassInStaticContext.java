/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldLocalClassInStaticContext {
    private int value;
    private static int counter;

    static Runnable task = () -> {
        class InLambda {
            void run(int value) { } // ok, local class in static context
        }
    };

    static {
        class InInitializer {
            void init(int value) { } // ok, local class in static context
        }
    }

    static void staticMethod() {
        class InStaticMethod {
            private int size;

            void use(int value) { } // ok, local class in static context

            void count(int counter) { } // violation ''counter' hides a field'

            class Member {
                void resize(int size) { } // violation ''size' hides a field'
            }
        }
    }

    static void staticMethodWithAnonymous() {
        new Object() {
            void anonymous() {
                class InAnonymous {
                    void use(int value) { } // ok, local class in static context
                }
            }
        };
    }

    void instanceMethod() {
        class InInstanceMethod {
            void use(int value) { } // violation ''value' hides a field'
        }
    }
}
