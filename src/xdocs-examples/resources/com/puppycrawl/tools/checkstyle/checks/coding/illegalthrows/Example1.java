/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalThrows"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

// xdoc section -- start
public class Example1 {
  public void func1() throws RuntimeException {} // violation
  public void func2() throws Exception {}
  public void func3() throws Error {}  // violation
  public void func4() throws Throwable {} // violation
  public void func5() throws NullPointerException {}
  @Override
  public String toString() throws Error {
    String str = "";
    return str;
  }
}
// xdoc section -- end
