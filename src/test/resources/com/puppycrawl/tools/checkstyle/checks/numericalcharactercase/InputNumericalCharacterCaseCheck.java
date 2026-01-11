/*
NumericalCharacterCase


*/

package com.puppycrawl.tools.checkstyle.checks.numericalcharactercase;

public class InputNumericalCharacterCaseCheck {

    int hex1 = 0X1A; // violation 'Should use lowercase numerical characters.'
    int hex2 = 0x1A;

    int bin1 = 0B1010; // violation 'Should use lowercase numerical characters.'
    int bin2 = 0b1010;

    float exp1 = 1.23E3f; // violation 'Should use lowercase numerical characters.'
    float exp2 = 1.23e3f;

    double hexExp1 = 0x1.3P2; // violation 'Should use lowercase numerical characters.'
    double hexExp2 = 0x1.3p2;

    float suf1 = 10F; // violation 'Should use lowercase numerical characters.'
    float suf2 = 10f;

    double suf3 = 10D; // violation 'Should use lowercase numerical characters.'
    double suf4 = 10d;

    float mix1 = 1.2E3F; // violation 'Should use lowercase numerical characters.'
    float mix2 = 1.2e3f;

    double mix3 = 0x1.3P2D; // violation 'Should use lowercase numerical characters.'
    double mix4 = 0x1.3p2D; // violation 'Should use lowercase numerical characters.'
    double mix5 = 0x1.3p2d;

    int ok1 = 0x1F;
    double ok2 = 123.456;
    double ok3 = 1 / 5432.0;
    double ok4 = 0x1E2.2p2d;
}
