/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
    <property name="checkFirstSentence" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

// xdoc section -- start
/**
 * Some description here
 */
public class Example4 {
  Example4() {

  }
  /**
   * Some description here.
   */
  private void testMethod1() {}
  // ok below, @return tag automatically inserts a period after the text
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
    // violation 3 lines above 'Incomplete HTML tag'
  }
  /**
   *
   */
  private void testEmptyMethod() {}
}
// xdoc section -- end
