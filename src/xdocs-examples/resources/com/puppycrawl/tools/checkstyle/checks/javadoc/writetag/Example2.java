/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tag" value="@since"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section -- start
/**
 * Some class
 */
public class Example2 {
  // violation 1 lines above 'Type Javadoc comment is missing @since tag.'
  /**
   * some doc
   * @since
   */
  void testMethod1() {} // ok, as methods are not checked by default

  /** some doc */
  public void testMethod2() {}

}
// xdoc section -- end
