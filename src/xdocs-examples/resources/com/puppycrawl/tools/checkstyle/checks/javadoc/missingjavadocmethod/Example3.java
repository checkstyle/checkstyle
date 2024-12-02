/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="scope" value="private"/>
      <property name="excludeScope" value="protected"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section -- start
public class Example3 {
  public Example3() {} // violation, missing javadoc for constructor
  public void testMethod1() {} // violation, missing javadoc for method
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  private void testMethod3() {} // violation, missing javadoc for method
  protected void testMethod4() {}
  void testMethod5() {} // violation, missing javadoc for method
}
// xdoc section -- end
