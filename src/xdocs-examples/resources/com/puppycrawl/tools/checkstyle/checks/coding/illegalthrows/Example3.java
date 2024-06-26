/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalThrows">
      <property name="ignoredMethodNames" value="f1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

// xdoc section -- start
public class Example3 {
  void f1() throws RuntimeException {}
  void f2() throws Exception {}
  void f3() throws Error {} // violation
  void f4() throws Throwable {} // violation
  void f5() throws NullPointerException {}
  @Override
  public String toString() throws Error {
    String str = "";
    return str;
  }
}
// xdoc section -- end
