/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableTestVariousAssignments {

    void method() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i -= 3; // violation 'Control variable 'i' is modified'
            k -= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method1() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i *= 3; // violation 'Control variable 'i' is modified'
            k *= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method2() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i /= 3; // violation 'Control variable 'i' is modified'
            k /= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method3() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i %= 3; // violation 'Control variable 'i' is modified'
            k %= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method4() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i >>= 3; // violation 'Control variable 'i' is modified'
            k >>= 4; // violation 'Control variable 'k' is modified'
        }
    }
    void method5() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i >>>= 3; // violation 'Control variable 'i' is modified'
            k >>>= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method6() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i <<= 3; // violation 'Control variable 'i' is modified'
            k <<= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method7() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i &= 3; // violation 'Control variable 'i' is modified'
            k &= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method8() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i ^= 3; // violation 'Control variable 'i' is modified'
            k ^= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method9() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i |= 3; // violation 'Control variable 'i' is modified'
            k |= 4; // violation 'Control variable 'k' is modified'
        }
    }

    void method10() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i--; // violation 'Control variable 'i' is modified'
            k--; // violation 'Control variable 'k' is modified'
        }
    }
}
