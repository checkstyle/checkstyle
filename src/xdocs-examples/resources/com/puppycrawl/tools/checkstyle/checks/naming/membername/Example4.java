/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName">
      <property name="applyToPublic" value="false"/>
      <property name="applyToProtected" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.naming.membername;

// xdoc section -- start
class Example4 {
  public int num1;
  protected int num2;
  int num3;
  private int num4;

  public int NUM1;
  protected int NUM2;
  int NUM3;           // violation 'Name 'NUM3' must match pattern'
  private int NUM4;   // violation 'Name 'NUM4' must match pattern'
}
// xdoc section -- end
