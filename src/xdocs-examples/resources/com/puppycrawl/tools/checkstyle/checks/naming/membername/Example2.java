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
  public int num1; // violation, name 'num1'
  // must watch pattern '^m[A-Z][a-zA-Z0-9]*$'
  protected int num2; // OK
  int num3; // OK
  private int num4; // violation, name 'num4'
  // must watch pattern '^m[A-Z][a-zA-Z0-9]*$'
}
// xdoc section -- end
