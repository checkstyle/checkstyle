/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="endOfSentenceFormat"
        value="([.。][ \t\n\r\f&lt;])|([.。]$)"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;
// xdoc section -- start
/**
 * Some description here。
 */
public class Example8 {
  Example8() {}
  /**
   * Some description here.
   */
  private void testMethod1() {}
  /**
   * Some description here。
   */
  private boolean testMethod2() {
    return true;
  }
  /**
   * Some description here?
   */
  private void testMethod3() {
    // violation 4 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here!
   */
  public void testMethod4() {
    // violation 4 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here
   * Second line of description
   */
  private void testMethod5() {
    // violation 5 lines above 'First sentence should end with a period'
  }
  /**
   * Some description here
   * <p
   */
  private void testMethod6() { // violation 4 lines above 'should end with a period'
    // violation 3 lines above 'Incomplete HTML tag found'
  }
  /**
   *
   */
  private void testEmptyMethod() {}
}
// xdoc section -- end
