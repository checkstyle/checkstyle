/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section - start

/**
 * Some class
 *
 */
public class Example1 {

  /**
   * some doc
   * @since
   */
  void testMethod1() {}

  /**
   * some doc
   * @since 1.6
   */
  void testMethod1WithNumSince() {}

  /**
   * some doc
   * @since 1.1-beta
   */
  void testMethod1WithAlphaSince() {}

  /** some doc */
  public void testMethod2() {}

}
// xdoc section - end
