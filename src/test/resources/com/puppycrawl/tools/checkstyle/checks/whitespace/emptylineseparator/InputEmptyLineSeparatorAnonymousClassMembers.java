/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorAnonymousClassMembers {

    void chainedMethodCalls() {
        new EarlyTerminatingScanner() {
            @Override
            protected void visit(Object role, Object element) {
            } // violation ''}' has more than 1 empty lines after.'


        }
        .setEnabled(true)
        .scan("ROLE", new Object());
    }

    void singleChainedMethodCall() {
        new EarlyTerminatingScanner() {
            @Override
            protected void visit(Object role, Object element) {
            } // violation ''}' has more than 1 empty lines after.'


        }
        .setEnabled(true);
    }

    void noChainedMethodCalls() {
        new EarlyTerminatingScanner() {
            @Override
            protected void visit(Object role, Object element) {
            } // violation ''}' has more than 1 empty lines after.'


        };
    }

    void namedLocalClass() {
        int value = 0; // violation 'There is more than 1 empty line after this line.'


        value++;

        class LocalScanner {
            void visit() {
            } // violation ''}' has more than 1 empty lines after.'


        } // violation 'There is more than 1 empty line after this line.'


        new LocalScanner().visit();
    }

    abstract static class EarlyTerminatingScanner {

        private boolean enabled;

        EarlyTerminatingScanner setEnabled(boolean value) {
            enabled = value;
            return this;
        }

        void scan(String role, Object element) {
            if (enabled) {
                visit(role, element);
            }
        }

        protected abstract void visit(Object role, Object element);
    }
}
