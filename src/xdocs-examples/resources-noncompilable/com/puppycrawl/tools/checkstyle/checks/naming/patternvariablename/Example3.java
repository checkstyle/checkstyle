/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableName">
        <property name="format" value="^[a-z][_a-zA-Z0-9]{2,}$"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

// xdoc section -- start
class Example3 {
  void foo(Object o1){
    if (o1 instanceof String STRING) {} // violation
    if (o1 instanceof Integer num) {}
    if (o1 instanceof Integer num_1) {}
    if (o1 instanceof Integer n) {} // violation
  }
}
// xdoc section -- end
