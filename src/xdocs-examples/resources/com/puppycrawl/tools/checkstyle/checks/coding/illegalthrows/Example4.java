/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalThrows">
      <property name="ignoreOverriddenMethods" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

// xdoc section -- start
public class Example4 {
  public void func1() throws RuntimeException {} // violation
  public void func2() throws Exception {}
  public void func3() throws Error {}  // violation
  public void func4() throws Throwable {} // violation
  public void func5() throws NullPointerException {}
  @Override
  public String toString() throws Error { // violation
    String str = "";
    return str;
  }
}
// xdoc section -- end
