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
public class Example3 {
  // café // violation 'Illegal symbol detected: 'é''
  int value = 1;
}
// xdoc section -- end
