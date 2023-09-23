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
  public int NUM1; // OK
  protected int NUM2; // violation, name 'NUM2'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
  int NUM3; // violation, name 'NUM3'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
  private int NUM4; // OK
}
// xdoc section -- end
