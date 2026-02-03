/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NumericalPrefixesInfixesSuffixesCharacterCase"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks
    .numericalprefixesinfixessuffixescharactercase;

// xdoc section -- start
public class Example1 {
  int hex1 = 0X1A; // violation 'Numerical prefix should be in lowercase.'
  int hex2 = 0x1A;

  int bin1 = 0B1010; // violation 'Numerical prefix should be in lowercase.'
  int bin2 = 0b1010;

  float exp1 = 1.23E3f; // violation 'Numerical infix should be in lowercase.'
  float exp2 = 1.23e3f;

  double hexEx1 = 0x1.3P2; // violation 'Numerical infix should be in lowercase.'
  double hexEx2 = 0x1.3p2;

  float suf1 = 10F; // violation 'Numerical suffix should be in lowercase.'
  float suf2 = 10f;

  double suf3 = 10D; // violation 'Numerical suffix should be in lowercase.'
  double suf4 = 10d;

  float mix1 = 1.2E3F; // violation 'Numerical infix should be in lowercase.'
  float mix3 = 1.2e3f;

  double mix4 = 0x1.3P2D; // violation 'Numerical infix should be in lowercase.'
  double mix5 = 0x1.3p2D; // violation 'Numerical suffix should be in lowercase.'
  double mix6 = 0x1.3p2d;

  int ok1 = 0x1F; // ok, here 'F' is a hexadecimal digit, not a numeric suffix.
  double ok2 = 123.456;
  double ok3 = 1 / 5432.0;

  // ok, here 'E' is a hexadecimal digit, not a numeric infix.
  double ok4 = 0x1E2.2p2d;
}
// xdoc section -- end
