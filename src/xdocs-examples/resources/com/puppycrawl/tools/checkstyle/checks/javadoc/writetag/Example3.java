/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
      <property name="tag" value="@since"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section - start

/**
 * Some class
 *
 */
public class Example3 {
  // violation 1 lines above 'Javadoc comment is missing @since tag.'
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
  // violation 1 lines above 'Javadoc comment is missing @since tag.'
}
// xdoc section - end
