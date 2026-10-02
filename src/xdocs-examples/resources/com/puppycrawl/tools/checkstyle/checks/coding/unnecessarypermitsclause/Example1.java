/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryPermitsClause"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

// xdoc section - start
public sealed class Example1 permits SubA, SubB {
    // violation above """Unnecessary 'permits' clause for classes
    // in the same compilation unit."""
}

final class SubA extends Example1 {
}

final class SubB extends Example1 {
}
// xdoc section - end
