/*
HexLiteralCase


*/

package com.puppycrawl.tools.checkstyle.checks.hexliteralcase;

public class InputHexLiteralCaseCheck2 {
    double c = 0x1ap1; // violation  'Should use uppercase hexadecimal letters.'
    double d = 0x1aP1d; // violation  'Should use uppercase hexadecimal letters.'
    double e = 0x1AbC.p1d; // violation  'Should use uppercase hexadecimal letters.'
    double f = 0x1AbC.P1D; // violation  'Should use uppercase hexadecimal letters.'
    float g = 0x1b.p1f; // violation  'Should use uppercase hexadecimal letters.'
    float h = 0x1b.P1F; // violation  'Should use uppercase hexadecimal letters.'
    float i = 0x1B.p1f;
    float k = 0x1f.P1F; // violation  'Should use uppercase hexadecimal letters.'
    double j = 0x1B.p1d;
}
