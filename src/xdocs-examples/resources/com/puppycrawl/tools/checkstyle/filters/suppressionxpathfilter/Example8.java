/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions8.xml"/>
    </module>
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example8 {

  int age = 23;

  public void changeAge() {
    age = 24; // filtered violation 'Reference to instance variable'
  }

  public int getAge() {
    return age; // violation, 'Reference to instance variable'
  }

}
// xdoc section -- end
