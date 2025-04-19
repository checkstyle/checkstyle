/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF, BLOCK_COMMENT_BEGIN" />
      <property name="tag" value="@since"/>
      <property name="tagFormat" value="[1-9\.]"/>
      <property name="tagSeverity" value="ignore"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section -- start
/**
 * Some class
 * @since 1.2
 */
public class Example4 {
  // violation 3 lines below 'Type Javadoc tag @since'
  /**
   * some doc
   * @since
   */
  void testMethod1() {}

  /** some doc */
  public void testMethod2() {}
  // violation 1 lines above 'Type Javadoc comment is missing @since tag.'
}
// xdoc section -- end
