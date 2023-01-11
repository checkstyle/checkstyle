/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableKillMutation {

    // case value 99 --> MINUS_ASSIGN
    void method() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i -= 3; // violation
            k -= 4; // violation
        }
    }

    // case value 100 --> STAR_ASSIGN
        void method1() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i *= 3; // violation
            k *= 4; // violation
        }
    }

//    case value 101 --> DIV_ASSIGN
        void method2() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i /= 3; // violation
            k /= 4; // violation
        }
    }

//    case value 102 --> MOD_ASSIGN
        void method3() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i %= 3; // violation
            k %= 4; // violation
        }
    }

//    case value 103 --> SR_ASSIGN
        void method4() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i >>= 3; // violation
            k >>= 4; // violation
        }
    }

//    case value 104 --> BSR_ASSIGN
        void method5() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i >>>= 3; // violation
            k >>>= 4; // violation
        }
    }

//    case value 105 --> SL_ASSIGN
        void method6() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i <<= 3; // violation
            k <<= 4; // violation
        }
    }

//    case value 106 --> BAND_ASSIGN
        void method7() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i &= 3; // violation
            k &= 4; // violation
        }
    }

//    case value 107 --> BXOR_ASSIGN
        void method8() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i ^= 3; // violation
            k ^= 4; // violation
        }
    }

//    case value 108 --> BOR_ASSIGN
        void method9() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i |= 3; // violation
            k |= 4; // violation
        }
    }

//    case value 26 --> BOR_ASSIGN
        void method10() {
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i--; // violation
            k--; // violation
        }
    }
}
