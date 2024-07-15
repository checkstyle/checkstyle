/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableName">
        <property name="id" value="FinalPatternVariableName"/>
        <property name="format" value="^[A-Z][A-Z0-9]*$"/>
    </module>

    <module name="PatternVariableName">
        <property name="id" value="NonFinalPatternVariableName"/>
        <property name="format" value="^([a-z][a-zA-Z0-9]*|_)$"/>
    </module>

    <module name="SuppressionXpathSingleFilter">
      <property name="id" value="FinalPatternVariableName"/>
      <property name="query" value="//PATTERN_VARIABLE_DEF[
            not(./MODIFIERS/FINAL)]/IDENT"/>
    </module>

    <module name="SuppressionXpathSingleFilter">
      <property name="id" value="NonFinalPatternVariableName"/>
      <property name="query" value="//PATTERN_VARIABLE_DEF[
            (./MODIFIERS/FINAL)]/IDENT"/>
    </module>
  </module>
</module>


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

// xdoc section -- start
class Example4 {
  void foo(Object o1){
    if (o1 instanceof String BAD) {} // violation
    if (o1 instanceof String good) {}
    if (o1 instanceof final String GOOD) {}
    if (o1 instanceof final String bad) {} // violation
  }
}
// xdoc section -- end
