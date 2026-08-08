/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF" />
      <property name="tag" value="@since"/>
      <property name="tagTextPattern" value="^[1-9\\.]+$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// xdoc section - start

/**
 * Some class
 * @since 1.1-beta
 */
public class Example4 {
  // violation 3 lines above 'Block tag 'since' is matched illegal pattern'
  /**
   * some doc
   * @todo remove
   * @since 1.1-beta
   */
  void testMethod1() {}

  /**
   * some doc
   * @since 1.6
   */
  void testMethod2() {}

  /**
   * some doc
   * @todo later
   * @since 1.2
   */
  void testMethod3() {}

}
// xdoc section - end
