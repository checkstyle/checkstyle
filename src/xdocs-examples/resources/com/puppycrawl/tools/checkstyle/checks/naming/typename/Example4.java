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
  public interface FirstName {}
  public class SecondName {}
  protected class ThirdName {}   // violation 'Name 'ThirdName' must match pattern'
  private class FourthName {}   // violation 'Name 'FourthName' must match pattern'
}
// xdoc section -- end
