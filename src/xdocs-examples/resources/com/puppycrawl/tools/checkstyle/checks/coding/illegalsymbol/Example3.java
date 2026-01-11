/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol">
      <property name="asciiOnly" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class Example3 {
  // Regular ASCII comment
  int x = 1;

  // café // violation 'Illegal Unicode symbol detected'
  int coffee = 2;

  // • bullet point // violation 'Illegal Unicode symbol detected'
  int item = 3;
}
// xdoc section -- end
