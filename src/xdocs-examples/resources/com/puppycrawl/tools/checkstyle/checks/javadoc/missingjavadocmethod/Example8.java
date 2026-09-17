/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section - start
public class Example8 {
  public Example8() {}
  // violation below 'Missing a Javadoc comment for 'testMethod1'.'
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
