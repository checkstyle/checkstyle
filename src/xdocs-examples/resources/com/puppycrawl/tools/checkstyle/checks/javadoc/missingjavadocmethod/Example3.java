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

// xdoc section - start
public class Example3 {
  public Example3() {} // violation 'Missing a Javadoc comment for 'Example3'.'
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
  // ok, protected method is excluded by excludeScope
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
