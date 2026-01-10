/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="checkEmptyJavadoc" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/**
 * Showcases the checkEmptyJavadoc property.
 */
public final class Example7 {

  /**
   *
   */
  // violation above 'JavadocStyleCheck.MSG_EMPTY'
  private void testMethod1() {}

  /**
   * This Javadoc is not empty.
   */
  private void testMethod2() {}

}
