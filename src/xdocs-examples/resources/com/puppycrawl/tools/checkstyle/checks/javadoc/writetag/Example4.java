/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
      <property name="tag" value="@since"/>
      <property name="tagFormat" value="^[1-9\.]+$"/>
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
public class Example4 {
  // violation 1 lines above 'Javadoc comment is missing @since tag.'
  /**
   * some doc
   * @since
   */
  void testMethod1() {}
  // violation 3 lines above 'Javadoc tag @since must match pattern'
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
  // violation 3 lines above 'Javadoc tag @since must match pattern'
  /** some doc */
  public void testMethod2() {}
  // violation 1 lines above 'Javadoc comment is missing @since tag.'
}
// xdoc section - end
