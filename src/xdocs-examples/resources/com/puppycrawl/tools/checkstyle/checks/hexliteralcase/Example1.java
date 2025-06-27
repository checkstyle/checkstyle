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
  int a =  0xabc;  // violation  'Should use uppercase hexadecimal letters.'
  int b = 0xABC;
  long l = 0Xf123L; // violation  'Should use uppercase hexadecimal letters.'
  long l = 0XF123L;

}
// xdoc section -- end
