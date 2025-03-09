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
  public interface I_firstName {}
  interface SecondName {} // violation 'Name 'SecondName' must match pattern'
}
// xdoc section -- end
