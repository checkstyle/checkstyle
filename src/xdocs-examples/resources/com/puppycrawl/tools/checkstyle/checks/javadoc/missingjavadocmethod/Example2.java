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
  public Example2() {} // violation, Missing a Javadoc comment
  public void testMethod1() {} // violation, Missing a Javadoc comment
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  private void testMethod3() {} // violation, Missing a Javadoc comment
  protected void testMethod4() {} // violation, Missing a Javadoc comment
  void testMethod5() {} // violation, Missing a Javadoc comment
}
// xdoc section -- end
