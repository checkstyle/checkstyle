/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName">
      <property name="applyToPublic" value="false"/>
      <property name="applyToPrivate" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.naming.membername;

// xdoc section -- start
class Example3 {
  public int NUM1;
  protected int NUM2; // violation 'Name 'NUM2' must match pattern'

  int NUM3; // violation 'Name 'NUM3' must match pattern'

  private int NUM4;
}
// xdoc section -- end
