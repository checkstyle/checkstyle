/*
EmptyBlock
option = text
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockDefault {
    void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }    // ok
        switch (a) {default: {}}    // violation
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}  // violation
        }
        switch (a) {
            default:
            {   // ok
            }
        }
    }

    void method2(int a) {
        switch (a) {
            case 1:a++;
            case 2:a++;
            default:    // ok
                switch (a) {
                    default: {  // ok

                    }
                }
        }
    }

    void method3(int a, int b) {
        switch (a) {
            case 1: break;
            default: {} method2(a);     // violation
        }

        switch (b) {
            case 2: break;
            default: method2(b); {}     // ok
        }

        switch (a+b) {case 1: break; default: {} ; }    // violation
    }

    void method4(int a, int b) {
        switch (a) {
            case 1:
            default: {}      // violation
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
            default: {      // ok

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
            {
            }   // ^ violation
        }

        switch (b) {
            default:       // ok
            case 1:
            case 2: { } method2(b);
            case 3:
        }
    }
}
