/*xml
<module name="Checker">
 <module name="TreeWalker">
   <module name="AvoidNestedBlocks"/>
 </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.avoidnestedblocks;

import static org.checkstyle.suppressionxpathfilter.interfaceistype.SuppressionXpathRegressionInterfaceIsType1.a;

// xdoc section -- end
public class Example1 {
  public void foo() {
    int myInteger = 0;
    {                      // violation, 'Avoid nested blocks.'
      myInteger = 2;
    }
    System.out.println("myInteger = " + myInteger);

    switch (a) {
      case 1: {                    // violation
        System.out.println("Case 1");
        break;
      }
      case 2:
        System.out.println("Case 2");     // OK
        break;
    }
  }
}
// xdoc section -- start
