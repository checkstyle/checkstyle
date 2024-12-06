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
  public Example3() {} // violation, 'Missing a Javadoc comment'
  public void testMethod1() {} // violation, 'Missing a Javadoc comment'
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  private void testMethod3() {} // violation, 'Missing a Javadoc comment'
  protected void testMethod4() {}
  void testMethod5() {} // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
