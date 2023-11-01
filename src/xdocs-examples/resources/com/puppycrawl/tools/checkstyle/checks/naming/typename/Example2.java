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
class Example2 {   // violation
  public interface firstName {}
  public class SecondName {} // violation
  protected class ThirdName {}
  private class FourthName {}
}
// xdoc section -- end
