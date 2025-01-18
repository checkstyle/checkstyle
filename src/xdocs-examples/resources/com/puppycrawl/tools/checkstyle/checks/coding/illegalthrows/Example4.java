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
  // violation below, 'Throwing 'RuntimeException' is not allowed'
  void f1() throws RuntimeException {}
  void f2() throws Exception {}
  void f3() throws Error {}  // violation, 'Throwing 'Error' is not allowed'
  void f4() throws Throwable {} // violation, 'Throwing 'Throwable' is not allowed'
  void f5() throws NullPointerException {}
  @Override // violation below, 'Throwing 'Error' is not allowed'
  public String toString() throws Error {
    String str = "";
    return str;
  }
}
// xdoc section -- end
