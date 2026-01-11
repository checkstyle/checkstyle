/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol">
      <property name="symbolCodes" value="0x0080-0x10FFFF"/>
      <message key="illegal.symbol"
               value="Only ASCII characters are allowed."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class Example5 {
  // café // violation 'Only ASCII characters are allowed.'
  int value = 1;
}
// xdoc section -- end
