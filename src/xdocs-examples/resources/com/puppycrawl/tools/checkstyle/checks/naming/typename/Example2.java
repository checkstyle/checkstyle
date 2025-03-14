/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
      <property name="applyToProtected" value="false"/>
      <property name="applyToPrivate" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

// xdoc section -- start
class Example2 {   // violation 'Name 'Example2' must match pattern'
  public interface firstName {}
  public class SecondName {} // violation 'Name 'SecondName' must match pattern'
  protected class ThirdName {}
  private class FourthName {}
}
// xdoc section -- end
