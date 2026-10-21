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

public class InputHiddenFieldImplicitlyStaticTypes {
    private int value;
    private static int counter;

    enum Color {
        RED {
            void paintConstant(int value) { } // ok, nested enum is implicitly static
        };

        private int shade;

        void paint(int value) { } // ok, nested enum is implicitly static

        void mix(int shade) { } // violation ''shade' hides a field'

        void count(int counter) { } // violation ''counter' hides a field'

        class Brush {
            void stroke(int value) { } // ok, nested enum is implicitly static
        }
    }

    interface Shape {
        class Square {
            void resize(int value) { } // ok, member class of interface is implicitly static

            void scale(int counter) { } // violation ''counter' hides a field'
        }
    }

    @interface Marker {
        class Holder {
            void hold(int value) { } // ok, member class of annotation is implicitly static
        }
    }

    void method() {
        enum Local {
            A;

            void use(int value) { } // ok, local enum is implicitly static
        }
    }

    class Inner {
        void use(int value) { } // violation ''value' hides a field'
    }
}
