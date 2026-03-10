/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="format" value="traditional"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;
// xdoc section -- start
/**
 * Some description here
 */
public class Example8 {
  Example8() {
    // violation 5 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here.
   */
  private void testMethod1() {}
  /**
   * {@return {@code true} if this object
   * has been initialized, {@code false} otherwise}
   */
  private boolean testMethod2() {
    return true;
  }
  /**
   * Some description here
   */
  private void testMethod3() {
    // violation 4 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here
   */
  public void testMethod4() {
    // violation 4 lines above 'First sentence should end with a period'
  }
}
// xdoc section -- end
