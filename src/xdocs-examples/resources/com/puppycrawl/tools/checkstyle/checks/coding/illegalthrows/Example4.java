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
  void f1() throws RuntimeException {} // violation
  void f2() throws Exception {}
  void f3() throws Error {}  // violation
  void f4() throws Throwable {} // violation
  void f5() throws NullPointerException {}
  @Override
  public String toString() throws Error { // violation
    String str = "";
    return str;
  }
}
// xdoc section -- end
