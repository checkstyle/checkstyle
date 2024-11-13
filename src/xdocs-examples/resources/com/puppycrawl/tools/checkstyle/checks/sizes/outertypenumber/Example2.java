/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeNumber">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;

public class Example2 {
}

class Examples {
  void exampleMethod() {}
}

enum Outertype1 {
}
// xdoc section -- end
