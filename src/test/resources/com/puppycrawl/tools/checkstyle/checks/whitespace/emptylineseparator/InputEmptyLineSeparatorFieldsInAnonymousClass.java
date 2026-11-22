/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorFieldsInAnonymousClass {
    Object anon = new Object() {
        int first;
        int second; // violation ''VARIABLE_DEF' should be separated from previous line.'
        void method() { // violation ''METHOD_DEF' should be separated from previous line.'
            int local = 1;
            int other = local;
        }

        int afterMethod;

        void last() {
        }
    };

    void inMethod() {
        Runnable r = new Runnable() {
            private int count;
            private int total; // violation ''VARIABLE_DEF' should be separated from previous line.'

            @Override
            public void run() {
                total = count;
            }
        };
        r.run();
    }

    static class Named {
        int first;
        int second; // violation ''VARIABLE_DEF' should be separated from previous line.'
    }

    enum Size {
        SMALL {
            int first;
            int second; // violation ''VARIABLE_DEF' should be separated from previous line.'
        }
    }
}
