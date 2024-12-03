/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="allowedAnnotations" value="Override,Deprecated"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section -- start
public class Example6 {
  public Example6() {} // violation, 'Missing a Javadoc comment'
  public void testMethod1() {} // violation, 'Missing a Javadoc comment'
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
