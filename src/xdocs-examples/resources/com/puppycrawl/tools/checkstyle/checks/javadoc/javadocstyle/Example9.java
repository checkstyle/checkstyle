/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="format" value="markdown"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;
// xdoc section -- start
/**
 * Some description here
 */
public class Example9 {
  Example9() {
    // ok, format=markdown skips all traditional /** Javadoc comments
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
    // ok, format=markdown skips traditional Javadoc - no violation
  }
  /**
   * Some description here
   */
  public void testMethod4() {
    // ok, format=markdown skips traditional Javadoc - no violation
  }
}
// xdoc section -- end
