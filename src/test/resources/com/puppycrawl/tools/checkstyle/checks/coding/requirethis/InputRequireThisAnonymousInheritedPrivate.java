/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousInheritedPrivate {

    int separator;
    int hidden;

    void helper() { }

    void privateField() {
        new Joiner() {
            void test() {
                separator = 1; // violation 'Reference to instance variable 'separator' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInheritedPrivate.this.separator = 1;
            }
        };
    }

    void privateMethod() {
        new Joiner() {
            void test() {
                helper(); // violation 'Method call to 'helper' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInheritedPrivate.this.helper();
            }
        };
    }

    void sameNameAsEnumConstant() {
        new Kind() {
            void test() {
                hidden = 1;
            }
        };
    }

    static class Joiner {
        private String separator;

        private void helper() { }

        void unrelated() { }
    }

    static class Kind {
    }

    static class Holder {
        enum Kind {
            hidden
        }
    }
}
