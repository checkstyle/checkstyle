/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.ArrayList;

public class InputRequireThisAnonymousInherited {

    int field;
    int deep;
    int other;

    void outerMethod() { }

    void sameFile() {
        new Base() {
            void test() {
                field = 1;
                outerMethod();
                other = 1; // violation 'Reference to instance variable 'other' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInherited.this.other = 1;
            }
        };
    }

    void transitive() {
        new Derived() {
            void test() {
                deep = 1;
                field = 1;
            }
        };
    }

    void qualifiedAndGeneric() {
        new InputRequireThisAnonymousInherited.Base() {
            void test() {
                field = 1;
            }
        };
        new GenericBase<String>() {
            void test() {
                field = 1;
            }
        };
    }

    void nested() {
        new Object() {
            void test() {
                new Base() {
                    void test2() {
                        field = 1;
                    }
                };
                other = 1; // violation 'Reference to instance variable 'other' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInherited.this.other = 1;
            }
        };
    }

    void unknownSuperType() {
        new ArrayList<String>() {
            void test() {
                field = 1; // violation 'Reference to instance variable 'field' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInherited.this.field = 1;
            }
        };
        new Runnable() {
            public void run() {
                outerMethod(); // violation 'Method call to 'outerMethod' needs .*'
            }

            void test2() {
                InputRequireThisAnonymousInherited.this.outerMethod();
            }
        };
    }

    static class Base {
        int field;
        void outerMethod() { }
    }

    static class GenericBase<T> {
        int field;
    }

    static class Derived extends Base {
        int deep;
    }
}
