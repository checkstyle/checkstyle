/*
TypeBodyPadding
atStartOfBody = (default)true
atEndOfBody = (default)true
allowEmpty = (default)true
skipLocal = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingDefault2 {

    class InnerClass {
        private int a;
    }

    interface InnerInterface {
        void method();
    }

    enum InnerEnum {
        A, B;
    }

    record InnerRecord(int a) {
        void method() {}
    }

    void method() {
        class LocalClass {
            private int a;
        }
    }

    InputTypeBodyPaddingDefault2() {
        class LocalClassInCtor {
            private int a;
        }
    }

    static {
        class LocalClassInStaticInit {
            private int a;
        }
    }

    {
        class LocalClassInInstanceInit {
            private int a;
        }
    }

    Runnable r = () -> {
        class LocalClassInLambda {
            private int a;
        }
    };

}
