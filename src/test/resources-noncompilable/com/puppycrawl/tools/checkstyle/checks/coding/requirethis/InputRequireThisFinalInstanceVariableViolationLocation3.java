/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: final variables are assigned and a constructor is duplicated

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinalInstanceVariableViolationLocation3 {
    final int a = 1;
    final int d = 2;
    final int e;
    final int f;
    final int g;
    final int h = 3;
    int other;
    InputRequireThisFinalInstanceVariableViolationLocation3Helper helper;

    {
        other = 0; // violation 'Reference to instance variable 'other' needs "this.".'
        this.other = 2;
        helper.g = 8; // violation 'Reference to instance variable 'helper' needs "this.".'
        class Local {
            int g;

            Local() {
                g = 5; // violation 'Reference to instance variable 'g' needs "this.".'
            }
        }
    }

    InputRequireThisFinalInstanceVariableViolationLocation3(int e) {
        a = a;
        // 2 violations above:
        //    'Reference to instance variable 'a' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
        other = a;
        // 2 violations above:
        //    'Reference to instance variable 'other' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
        consume(a);
        // 2 violations above:
        //    'Method call to 'consume' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
        {
            d = a;
            // 2 violations above:
            //    'Reference to instance variable 'd' needs "this.".'
            //    'Reference to instance variable 'a' needs "this.".'
        }
        {
            d = d;
            // 2 violations above:
            //    'Reference to instance variable 'd' needs "this.".'
            //    'Reference to instance variable 'd' needs "this.".'
        }
        e = e; // violation 'Reference to instance variable 'e' needs "this.".'
        f = f;
        // 2 violations above:
        //    'Reference to instance variable 'f' needs "this.".'
        //    'Reference to instance variable 'f' needs "this.".'
        h = this.h; // violation 'Reference to instance variable 'h' needs "this.".'
    }

    InputRequireThisFinalInstanceVariableViolationLocation3(int g) {
        g = g; // violation 'Reference to instance variable 'g' needs "this.".'
    }

    void method() {
        a = a;
        // 2 violations above:
        //    'Reference to instance variable 'a' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
        other = a;
        // 2 violations above:
        //    'Reference to instance variable 'other' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
    }

    void assignInMethod() {
        e = 1; // violation 'Reference to instance variable 'e' needs "this.".'
    }

    void consume(int value) {
    }
}

class InputRequireThisFinalInstanceVariableViolationLocation3Helper {
    int g;
}
