/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidNestedBlocks">
      <property name="allowInSwitchCase" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.avoidnestedblocks;

import static org.checkstyle.suppressionxpathfilter.interfaceistype.InputXpathInterfaceIsTypeAllowMarker.a;

// xdoc section -- start
public class Example2 {
  public void foo() {
    int myInteger = 0;
    {                      // violation 'Avoid nested blocks'
      myInteger = 2;
    }
    System.out.println("myInteger = " + myInteger);

    switch (a) {
      case 1: {
        System.out.println("Case 1");
        break;
      }
      case 2:
        System.out.println("Case 2");
        break;
    }
  }
}
// xdoc section -- end
