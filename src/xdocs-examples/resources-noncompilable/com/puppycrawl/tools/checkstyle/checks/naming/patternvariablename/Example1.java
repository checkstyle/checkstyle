/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableName"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

// xdoc section -- start
class Example1 {
  Example1(Object o1){
    if (o1 instanceof String STRING) { // violation
    }
    if (o1 instanceof Integer num) {
    }
    if (o1 instanceof Integer num_1) { // violation
    }
  }
}
// xdoc section -- end
