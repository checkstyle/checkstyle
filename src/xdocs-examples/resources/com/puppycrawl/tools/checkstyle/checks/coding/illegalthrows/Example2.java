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
  public void func1() throws RuntimeException {}
  public void func2() throws Exception {}
  public void func3() throws Error {}
  public void func4() throws Throwable {}
  public void func5() throws NullPointerException {} // violation
  @Override
  public String toString() throws Error {
    String str = "";
    return str;
  }
}
// xdoc section -- end
