/*
TypeBodyPadding
atStartOfBody = (default)true
atEndOfBody = (default)true
allowEmpty = (default)true
skipLocal = (default)true
skipInner = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingSkipInnerFalse {

    // violation below 'Blank line required after the opening brace of type definition.'
    class InnerClass {
        private int a;
    } // violation 'Blank line required before the closing brace of type definition.'

    // violation below 'Blank line required after the opening brace of type definition.'
    interface InnerInterface {
        void method();
    } // violation 'Blank line required before the closing brace of type definition.'

    // violation below 'Blank line required after the opening brace of type definition.'
    enum InnerEnum {
        A, B;
    } // violation 'Blank line required before the closing brace of type definition.'

    // violation below 'Blank line required after the opening brace of type definition.'
    record InnerRecord(int a) {
        void method() {}
    } // violation 'Blank line required before the closing brace of type definition.'

    void method() {
        class LocalClass {
            private int a;
        }
    }

    InputTypeBodyPaddingSkipInnerFalse() {
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
