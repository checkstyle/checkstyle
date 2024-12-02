/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="scope" value="private"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section -- start
public class Example2 {
  public Example2() {} // violation, missing javadoc for method
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
  protected void testMethod4() {} // violation, missing javadoc for method
  void testMethod5() {} // violation, missing javadoc for method
}
// xdoc section -- end
