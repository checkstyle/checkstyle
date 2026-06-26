/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format"
        value="^I_[a-zA-Z0-9]*$"/>
      <property name="tokens"
        value="INTERFACE_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

// xdoc section -- start
class Example3 {
  public interface firstName {} // violation 'Name 'firstName' must match pattern'
  public class SecondName {}
  protected class Third_Name {}
  private class FourthName_ {}
  enum Fifth_Name {}
}
// xdoc section -- end
