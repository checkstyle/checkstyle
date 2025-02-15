/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalThrows"/>
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
  // filtered violation below 'Throwing 'RuntimeException' is not allowed.'
  public void throwsMethod() throws RuntimeException {
  }

  // violation below, 'Throwing 'RuntimeException' is not allowed.'
  public void sampleMethod() throws RuntimeException {
  }
}
// xdoc section -- end
