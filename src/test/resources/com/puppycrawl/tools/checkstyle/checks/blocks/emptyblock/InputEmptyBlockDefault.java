/*
EmptyBlock
option = TEXT
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockDefault {
    void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }
        switch (a) {default: {}}    // violation 'Empty default block'
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}  // violation 'Empty default block'
        }
        switch (a) {
            default:
            {   // ok as the block contains a comment
            }
        }
    }

    void method2(int a) {
        switch (a) {
            case 1:a++;
            case 2:a++;
            default:
                switch (a) {
                    default: {  // ok as the block contains a comment

                    }
                }
        }
    }

    void method3(int a, int b) {
        switch (a) {
            case 1: break;
            default: {} method2(a);     // violation 'Empty default block'
        }

        switch (b) {
            case 2: break;
            default: method2(b); {}
        }

        switch (a+b) {case 1: break; default: {} ; }    // violation 'Empty default block'
    }

    void method4(int a, int b) {
        switch (a) {
            case 1:
            default: {}      // violation 'Empty default block'
        }

        switch (b) {
            case 1:
            default:
        }

        switch (a+b) {
            default:
            case 1: { }
        }

        switch (a-b) {
            case 1:
            default: {      // ok as the block contains a comment

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
            }   // violation above 'Empty default block'
        }

        switch (b) {
            default:
            case 1:
            case 2: { } method2(b);
            case 3:
        }
    }
}
