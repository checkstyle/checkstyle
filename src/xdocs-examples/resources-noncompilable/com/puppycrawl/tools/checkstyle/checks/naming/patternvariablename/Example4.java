/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="PatternVariableName"/>
      <property name="query" value="//PATTERN_VARIABLE_DEF[
            not(./MODIFIERS/FINAL)]/IDENT"/>
    </module>
  </module>
</module>


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

// xdoc section -- start
class Example4 {
  void foo(Object o1){
    // ok below, not a final pattern variable
    if (o1 instanceof String BAD) {}
    if (o1 instanceof final String BAD) {} // violation
  }
}
// xdoc section -- end
