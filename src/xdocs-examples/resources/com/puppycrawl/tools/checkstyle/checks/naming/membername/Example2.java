/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName">
      <property name="format" value="^m[A-Z][a-zA-Z0-9]*$"/>
      <property name="applyToProtected" value="false"/>
      <property name="applyToPackage" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.naming.membername;

// xdoc section -- start
class Example2 {
  public int num1; // violation 'Name 'num1' must match pattern'

  protected int num2;
  int num3;
  private int num4; // violation 'Name 'num4' must match pattern'

}
// xdoc section -- end
