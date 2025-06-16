/*
HexLiteralCase


*/

package resources.com.puppycrawl.tools.checkstyle.checks.hexliteralcase;

public class InputHexLiteralCaseCheck {
    int i =  0xabc;  // violation  'Should use uppercase hexadecimal digit instead of 'a, b, c'.'
    int a = 0xABC;
    int k = 0XCAFEBAB1;
    long d = 0Xf123L; // violation  'Should use uppercase hexadecimal digit instead of 'f'.'
    int binaryValue = 0b1010;
    int num = 1;


}
