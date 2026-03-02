/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="validatePatternVariables" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start

class Example6 {
  public void method() {
    final Object o = 45;
    // violation below, 'Variable 'p' should be declared final'
    if (o instanceof String p) {
      System.out.println(p);
    }
    if (o instanceof String p) { // ok
      p = "rewrite";
      System.out.println(p);
    }
    final boolean value = o instanceof String p;
    // ok above, because p can't be reassigned
  }
}
// xdoc section -- end
