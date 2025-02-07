/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="IllegalThrows"/>
      <property name="query" value="//LITERAL_THROWS/IDENT[@text='RuntimeException'
                    and ./ancestor::METHOD_DEF[./IDENT[@text='throwsMethod']]]"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example12 {
  public void throwsMethod() throws RuntimeException { // OK, violation will be suppressed
  }

  public void sampleMethod() throws RuntimeException { // violation, Throwing 'RuntimeException' is not allowed
  }
}
// xdoc section -- end
