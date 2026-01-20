/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryPermitsClause"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

// xdoc section -- start
public sealed class Example1 permits A, B {
// violation above 'permits clause unnecessary for 'Example1', all subclasses in same file.'
}

final class A extends Example1 {
}

final class B extends Example1 {
}
// xdoc section -- end
