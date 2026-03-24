/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousClassMemberLookup {
    int field = 0;

    void bar() {}

    void method() {
        new Object() {
            int field = 1;

            void bar() {}

            void foo() {
                field = 2; // violation 'Reference to instance variable 'field' needs "this.".'
                bar(); // violation 'Method call to 'bar' needs "this.".'
            }
        };
    }
}

