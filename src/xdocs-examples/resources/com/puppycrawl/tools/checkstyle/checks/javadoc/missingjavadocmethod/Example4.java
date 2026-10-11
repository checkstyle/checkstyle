/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="ignoreMethodNamesRegex" value="^testMethod.*$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section - start
public class Example4 {
  public Example4() {} // violation 'Missing a Javadoc comment for 'Example4'.'
  // ok, method name matches ignoreMethodNamesRegex
  public void testMethod1() {}
  /**
   * Some description here.
   */
  public void testMethod2() {}

  @Override
  public String toString() {
    return "Some string";
  }

  // ok, method name matches ignoreMethodNamesRegex
  private void testMethod3() {}
  // ok, method name matches ignoreMethodNamesRegex
  protected void testMethod4() {}
  void testMethod5() {}

  // ok, method name matches ignoreMethodNamesRegex
  public void testMethod6() {
    System.out.println("line 1");
    System.out.println("line 2");
    System.out.println("line 3");
  }
}
// xdoc section - end
