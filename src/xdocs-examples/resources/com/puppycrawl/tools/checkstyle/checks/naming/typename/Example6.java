/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
      <property name="tokens" value="ENUM_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

// xdoc section - start
class Example6 {
  public interface firstName {}
  public class SecondName {}
  protected class Third_Name {}
  private class FourthName_ {}
  enum Fifth_Name {} // violation 'Name 'Fifth_Name' must match pattern'
}
// xdoc section - end
