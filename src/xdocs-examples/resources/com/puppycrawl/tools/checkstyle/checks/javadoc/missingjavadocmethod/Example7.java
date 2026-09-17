/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="minLineCount" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section - start
public class Example7 {
  public Example7() {}
  // ok, method has fewer lines than minLineCount
  public void testMethod1() {}
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  // ok, private method is not checked by default
  private void testMethod3() {}
  // ok, protected method is not checked by default
  protected void testMethod4() {}
  void testMethod5() {}

  // violation below 'Missing a Javadoc comment for 'testMethod6'.'
  public void testMethod6() {
    System.out.println("line 1");
    System.out.println("line 2");
    System.out.println("line 3");
  }
}
// xdoc section - end
