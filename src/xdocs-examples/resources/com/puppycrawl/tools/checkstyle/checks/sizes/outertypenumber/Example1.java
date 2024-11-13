/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeNumber"/>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;// violation, Outer types defined is '3' (max allowed is '1')

public class Example1 {
}

class Example {
    void exampleMethod() {}
}

enum Outertype {
}
// xdoc section -- end
