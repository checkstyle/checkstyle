/*
HiddenField
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT
ignoreFormat = (default)(null)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.function.IntConsumer;

public class InputHiddenFieldAnonymousClassFields {

    Runnable field = new Runnable() {
        private int count;

        void setCount(int count) { // violation ''count' hides a field'
            this.count = count;
        }

        @Override
        public void run() {
            int count = 1; // violation ''count' hides a field'
            System.out.println(count + this.count);
        }
    };

    static void inStaticMethod() {
        Runnable r = new Runnable() {
            private int count;
            private static int shared;

            void set(int count) { // violation ''count' hides a field'
            }

            static void util(int count, int shared) { // violation ''shared' hides a field'
            }

            @Override
            public void run() {
                Object nested = new Object() {
                    void m(int count, int other) { // violation ''count' hides a field'
                    }
                };
                IntConsumer consumer = count -> { }; // violation ''count' hides a field'
                class Local {
                    void n(int count) { // violation ''count' hides a field'
                    }
                }
                record Rec() {
                    void n(int count) { // ok, a record is static
                    }
                }
                enum En {
                    A;

                    void n(int count) { // ok, an enum is static
                    }
                }
                interface Iface {
                    default void n(int count) { // ok, an interface is static
                    }
                }
            }
        };
    }

    void notHidden() {
        Object o = new Object() {
            private int value;

            static {
                int value = 1; // ok, an instance field is not visible in a static initializer
            }

            static class Nested {
                void n(int value) { // ok, a static class does not see the instance field
                }
            }

            void other(int count) {
            }
        };
    }
}
