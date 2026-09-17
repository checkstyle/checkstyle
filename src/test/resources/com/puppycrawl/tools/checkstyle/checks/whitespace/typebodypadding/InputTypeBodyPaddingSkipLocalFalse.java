/*
TypeBodyPadding
atStartOfBody = (default)true
atEndOfBody = (default)true
allowEmpty = (default)true
skipLocal = false
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingSkipLocalFalse {

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
        // violation below 'Blank line required after the opening brace of type definition.'
        class LocalClass {
            private int a;
        } // violation 'Blank line required before the closing brace of type definition.'
    }

    InputTypeBodyPaddingSkipLocalFalse() {
        // violation below 'Blank line required after the opening brace of type definition.'
        class LocalClassInCtor {
            private int a;
        } // violation 'Blank line required before the closing brace of type definition.'
    }

    static {
        // violation below 'Blank line required after the opening brace of type definition.'
        class LocalClassInStaticInit {
            private int a;
        } // violation 'Blank line required before the closing brace of type definition.'
    }

    {
        // violation below 'Blank line required after the opening brace of type definition.'
        class LocalClassInInstanceInit {
            private int a;
        } // violation 'Blank line required before the closing brace of type definition.'
    }

    Runnable r = () -> {
        // violation below 'Blank line required after the opening brace of type definition.'
        class LocalClassInLambda {
            private int a;
        } // violation 'Blank line required before the closing brace of type definition.'
    };

}
