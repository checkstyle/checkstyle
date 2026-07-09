/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol">
      <property name="symbolCodes" value="0x0080-0x10FFFF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class UseCase1 {
  // café // violation 'Illegal symbol detected: 'é''

  int value1 = 1;
  String value2 = "Hello 😀";
}
// xdoc section -- end
