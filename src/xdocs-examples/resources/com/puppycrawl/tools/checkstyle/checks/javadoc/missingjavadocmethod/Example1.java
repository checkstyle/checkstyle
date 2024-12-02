/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section -- start
public class Example1 {
  public Example1() {} // violation, missing javadoc for constructor
  public void testMethod1() {} // violation, missing javadoc for method
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  private void testMethod3() {}
  protected void testMethod4() {}
  void testMethod5() {}
}
// xdoc section -- end
