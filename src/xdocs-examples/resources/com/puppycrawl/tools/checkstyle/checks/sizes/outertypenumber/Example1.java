/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeNumber"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;
// xdoc section -- start
// violation 2 lines above 'Outer types defined is 2 (max allowed is 1)'

public class Example1 {
    enum outer {
    }
}

class ExampleOne {
    void exampleMethod() {}
}
// xdoc section -- end
