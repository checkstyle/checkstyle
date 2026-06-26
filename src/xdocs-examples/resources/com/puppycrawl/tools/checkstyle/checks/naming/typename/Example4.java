/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
      <property name="applyToPublic" value="false"/>
      <property name="applyToPackage" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

// xdoc section -- start
class Example4 {
  public interface firstName {}
  public class SecondName {}
  protected class Third_Name {}  // violation 'Name 'Third_Name' must match pattern'
  private class FourthName_ {}   // violation 'Name 'FourthName_' must match pattern'
  enum Fifth_Name {}
}
// xdoc section -- end
