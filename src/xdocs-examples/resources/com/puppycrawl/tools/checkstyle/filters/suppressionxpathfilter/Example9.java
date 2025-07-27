/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions9.xml"/>
    </module>
    <module name="IllegalThrows"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example9 {
  // filtered violation below "Throwing 'RuntimeException' is not allowed."
  public void throwsMethod() throws RuntimeException {
  }

  // violation below, "Throwing 'RuntimeException' is not allowed."
  public void sampleMethod() throws RuntimeException {
  }
}
// xdoc section -- end
