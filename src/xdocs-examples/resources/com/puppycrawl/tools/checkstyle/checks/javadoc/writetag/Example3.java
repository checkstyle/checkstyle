/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF, BLOCK_COMMENT_BEGIN" />
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
public class Example3 {
  // violation 1 lines above 'Type Javadoc comment is missing @since tag.'
  // violation 3 lines below 'Javadoc tag @since='
  /**
   * some doc
   * @since
   */
  void testMethod1() {}

  /** some doc */
  public void testMethod2() {}

}
// xdoc section -- end
