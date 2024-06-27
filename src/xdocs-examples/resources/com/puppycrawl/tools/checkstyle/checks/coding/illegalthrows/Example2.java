/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalThrows">
      <property name="illegalClassNames" value="NullPointerException"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

// xdoc section -- start
public class Example2 {
  void f1() throws RuntimeException {}
  void f2() throws Exception {}
  void f3() throws Error {}
  void f4() throws Throwable {}
  void f5() throws NullPointerException {} // violation
  @Override
  public String toString() throws Error {
    String str = "";
    return str;
  }
}
// xdoc section -- end
