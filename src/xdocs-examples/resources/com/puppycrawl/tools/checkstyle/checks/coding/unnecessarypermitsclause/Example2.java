/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryPermitsClause"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

// xdoc section -- start
public sealed interface Example2 permits A, B {
    // ok as all permitted subclasses are not in same file
}

final class A implements Example2 {
}

// xdoc section -- end
