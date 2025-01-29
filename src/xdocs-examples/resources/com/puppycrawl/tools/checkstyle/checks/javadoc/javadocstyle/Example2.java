/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
    <property name="scope" value="public"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

// xdoc section -- start
/**
 * Some description here
 */
public class Example2 {
  Example2() {
    // violation 5 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here.
   */
  private void testMethod1() {}
  //below, @return tag automatically inserts a period after the text
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

  }
  /**
   * Some description here
   */
  public void testMethod4() {
    // violation 4 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here
   * Second line of description
   */
  private void testMethod5() {

  }
  /**
   * Some description here
   * <p
   */
  private void testMethod6() {

  }
}
// xdoc section -- end
