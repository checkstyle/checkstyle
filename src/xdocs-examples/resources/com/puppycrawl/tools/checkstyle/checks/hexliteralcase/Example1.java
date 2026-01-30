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
  byte b1  = 0x1b;          // violation  'Should use uppercase hexadecimal letters.'
  byte b2  = 0x1B;

  short s1 = 0xF5f;         // violation  'Should use uppercase hexadecimal letters.'
  short s2 = 0xF5F;

  int i1 = 0x11 + 0xabc;    // violation  'Should use uppercase hexadecimal letters.'
  int i2 = 0x11 + 0xABC;
  int i3 = 0X123 + 0Xabc;   // violation  'Should use uppercase hexadecimal letters.'
  int i4 = 0X123 + 0XABC;
  int i5 = 0xdeadbeef;      // violation  'Should use uppercase hexadecimal letters.'
  int i6 = 0xDEADBEEF;

  long l1 = 0x7fff_ffff_ffff_ffffL;
  // violation above 'Should use uppercase hexadecimal letters.'
  long l2 = 0x7FFF_FFFF_FFFF_FFFFL;

  long l3 = 0x7FFF_AAA_bBB_DDDL;
  // violation above 'Should use uppercase hexadecimal letters.'
  long l4 = 0x7FFF_AAA_BBB_DDDL;

  float c = 0x1ap1f; // violation  'Should use uppercase hexadecimal letters.'
  float c2 = 0x1Ap1f;
  double e = 0x1AbC.p1d; // violation  'Should use uppercase hexadecimal letters.'
  double e2 = 0x1ABC.p1d;
  double f = 0x1AbC.P1D; // violation  'Should use uppercase hexadecimal letters.'
  double f2 = 0x1ABC.P1D;
  float g = 0x1b.p1f; // violation  'Should use uppercase hexadecimal letters.'
  float g2 = 0x1B.p1f;
  float h = 0x1b.P1F; // violation  'Should use uppercase hexadecimal letters.'
  float h2 = 0x1B.P1F;
  float i = 0x1B.p1f;
  double j = 0x1B.p1d;

}
// xdoc section -- end
