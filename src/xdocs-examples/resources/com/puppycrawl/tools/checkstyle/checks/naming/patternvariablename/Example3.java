/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableName">
      <property name="format" value="^[a-z][_a-zA-Z0-9]{2,}$"/>
    </module>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

// xdoc section -- start
class Example3 {
  void foo(Object o1){
    if (o1 instanceof String STRING) {}
    // violation above, 'Name 'STRING' must match pattern*.'
    if (o1 instanceof Integer num) {}
    if (o1 instanceof Integer num_1) {}
    if (o1 instanceof Integer n) {}
    // violation above, 'Name 'n' must match pattern*.'
  }
}
// xdoc section -- end
