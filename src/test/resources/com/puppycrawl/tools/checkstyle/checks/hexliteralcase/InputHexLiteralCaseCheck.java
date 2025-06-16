/*
HexLiteralCase


*/

package resources.com.puppycrawl.tools.checkstyle.checks.hexliteralcase;

public class InputHexLiteralCaseCheck {
    int h = 0xb;                // violation  'Should use uppercase hexadecimal letters.'
    int w = 0xB;
    int i = 0xabc;             // violation  'Should use uppercase hexadecimal letters.'
    int y = 0xABC;
    long d = 0Xf123L;          // violation  'Should use uppercase hexadecimal letters.'
    long x = 0xF123L;
    int a = 0xdeadbeef;        // violation  'Should use uppercase hexadecimal letters.'
    int b = 0xDEADBEEF;
    int c = 0x123abc;          // violation  'Should use uppercase hexadecimal letters.'
    int f = 0x123ABC;
    long l = 0xabcDEF12345L;   // violation  'Should use uppercase hexadecimal letters.'
    long s = 0xABCDEF12345L;
    int binary = 0b1010;

}
