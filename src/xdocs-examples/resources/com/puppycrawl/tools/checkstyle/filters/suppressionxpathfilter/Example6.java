/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions6.xml"/>
    </module>
    <module name="LocalVariableName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example6 {

  public void testMethod() {

    int TestVariable; // filtered violation 'Name 'TestVariable' must match pattern'

    int WeirdName; // violation, "Name 'WeirdName' must match pattern"
  }

}
// xdoc section -- end
