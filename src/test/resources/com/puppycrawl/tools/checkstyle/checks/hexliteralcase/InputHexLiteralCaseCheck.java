/*
HexLiteralCase


*/

package resources.com.puppycrawl.tools.checkstyle.checks.hexliteralcase;

public class InputHexLiteralCaseCheck {
    int h = 0xb;                       // violation  'Should use uppercase hexadecimal letters.'
    int w = 0xB;
    long d = 0Xf123L;                  // violation  'Should use uppercase hexadecimal letters.'
    long x = 0xF123L;
    byte b1  = 0x1b;                   // violation  'Should use uppercase hexadecimal letters.'
    byte b2  = 0x1B;
    short s2 = 0xF5f;                  // violation  'Should use uppercase hexadecimal letters.'
    short s3  = 0xF6F;
    short s1 = 0xA8C;
    int i1 = 0x11 + 0xabc;             // violation  'Should use uppercase hexadecimal letters.'
    int i2 = 0x11 + 0xABC;
    int i3 = 0X123 + 0Xabc;           // violation  'Should use uppercase hexadecimal letters.'
    int i4 = 0X123 + 0XABC;
    int i5 = 0xdeadbeef;              // violation  'Should use uppercase hexadecimal letters.'
    int i6 = 0xDEADBEEF;
    long l1 = 0x7fff_ffff_ffff_ffffL; // violation  'Should use uppercase hexadecimal letters.'
    long l2 = 0x7FFF_FFFF_FFFF_FFFFL;
    long l3 = 0x7FFF_AAA_bBB_DDDL;    // violation  'Should use uppercase hexadecimal letters.'
    long l4 = 0x7FFF_AAA_BBB_DDDL;
    int binary = 0b1010;

}
