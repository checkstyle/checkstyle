/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol">
      <property name="symbolCodes" value="0x1F300-0x1F5FF,  0x1F680-0x1F6FF"/>
    </module>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class Example2 {
  int x = 1;

  // 🌟 Star // violation 'Illegal Unicode symbol detected'
  int star = 3;

  // 🚀 Rocket // violation 'Illegal Unicode symbol detected'
  int fast = 3;
}
// xdoc section -- end
