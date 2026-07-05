/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeNumber">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;
// xdoc section -- start


public class Example2 {
    enum outer {
    }
}

class ExampleTwo {
    void exampleMethod() {}
}
// xdoc section -- end
