/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

// xdoc section -- start
public class Example1 {
  private String name;
  private int age;

  public void setName(String name) {
    this.name = name;   // ok, "this" is required here
  }

  public void setAge(int age) {
    this.age = age;   // ok, "this" is required here
  }
}
// xdoc section -- end
