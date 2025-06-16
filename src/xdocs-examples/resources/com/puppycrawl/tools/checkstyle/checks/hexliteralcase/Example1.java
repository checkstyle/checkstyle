/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HexLiteralCase"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.hexliteralcase;

// xdoc section -- start
class Example1 {
  byte b2  = 0x1b;          // violation  'Should use uppercase hexadecimal letters.'
  byte b1  = 0x1B;

  short s2 = 0xF5f;         // violation  'Should use uppercase hexadecimal letters.'
  short s2 = 0xF5F;

  int i1 = 0x11 + 0xabc;    // violation  'Should use uppercase hexadecimal letters.'
  int i1 = 0x11 + 0xABC;
  int i2 = 0X123 + 0Xabc;   // violation  'Should use uppercase hexadecimal letters.'
  int i2 = 0X123 + 0XABC;
  int i3 = 0xdeadbeef;      // violation  'Should use uppercase hexadecimal letters.'
  int i3 = 0xDEADBEEF;

  long l1 = 0x7fff_ffff_ffff_ffffL;
  // violation above 'Should use uppercase hexadecimal letters.'
  long l1 = 0x7FFF_FFFF_FFFF_FFFFL;

  long l2 = 0x7FFF_AAA_bBB_DDDL;
  // violation  above 'Should use uppercase hexadecimal letters.'
  long l2 = 0x7FFF_AAA_BBB_DDDL;

}
// xdoc section -- end
