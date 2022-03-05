/*
EmptyBlock
option = (default)STATEMENT
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockDefault2 {
    void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }    // ok
        switch (a) {default: {}}    // violation 'Must have at least one statement'
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}  // violation 'Must have at least one statement'
        }
        switch (a) {
            default:
            {   // violation 'Must have at least one statement'
            }
        }
    }

    void method2(int a) {
        switch (a) {
            case 1:a++;
            case 2:a++;
            default:    // ok
                switch (a) {
                    default: {  // violation 'Must have at least one statement'

                    }
                }
        }
    }

    void method3(int a, int b) {
        switch (a) {
            case 1: break;
            default: {} method2(a);     // violation 'Must have at least one statement'
        }

        switch (b) {
            case 2: break;
            default: method2(b); {}     // ok
        }

        switch (a+b) {case 1: break; default: {} ; }// violation 'Must have at least one statement'
    }

    void method4(int a, int b) {
        switch (a) {
            case 1:
            default: {}      // violation 'Must have at least one statement'
        }

        switch (b) {
            case 1:
            default:        // ok
        }

        switch (a+b) {
            default:        // ok
            case 1: { }
        }

        switch (a-b) {
            case 1:
            default: {      // violation 'Must have at least one statement'

            } ;
            case 2: { }
        }
    }

    void method5(int a, int b) {
        switch (a) {
            case 1:
            case 2:
            case 3:
            default:
            {               // violation 'Must have at least one statement'
            }
        }

        switch (b) {
            default:       // ok
            case 1:
            case 2: { } method2(b);
            case 3:
        }
    }
}
