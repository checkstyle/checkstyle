/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public class InputUnnecessarySemicolonAfterTypeMemberDeclarationAnonymous {
    Object anon = new Object() {
        ; // violation 'Unnecessary semicolon.'
        int field;
    };

    Object anonMethod = new Object() {
        ; // violation 'Unnecessary semicolon.'
        void method() {
        }
    };

    Object anonOk = new Object() {
        int field;
    };

    void inMethod() {
        Runnable r = new Runnable() {
            ; // violation 'Unnecessary semicolon.'
            @Override
            public void run() {
            }
        };
        r.run();
    }

    Object anonWithClass = new Object() {
        ; // violation 'Unnecessary semicolon.'
        class Inner {
        }
    };

    enum Size {
        SMALL {
            ; // violation 'Unnecessary semicolon.'
            int field;
        },
        MEDIUM {
            ; // violation 'Unnecessary semicolon.'
        },
        LARGE {
            int field;
        },
        HUGE
    }
}
