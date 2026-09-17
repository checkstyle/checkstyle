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

// xdoc section - start
public class Example2 {
  public Example2() {} // violation 'Missing a Javadoc comment for 'Example2'.'
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

  // violation below 'Missing a Javadoc comment for 'testMethod3'.'
  private void testMethod3() {}
  // violation below 'Missing a Javadoc comment for 'testMethod4'.'
  protected void testMethod4() {}
  void testMethod5() {} // violation 'Missing a Javadoc comment for 'testMethod5'.'

  // violation below 'Missing a Javadoc comment for 'testMethod6'.'
  public void testMethod6() {
    System.out.println("line 1");
    System.out.println("line 2");
    System.out.println("line 3");
  }
}
// xdoc section - end
