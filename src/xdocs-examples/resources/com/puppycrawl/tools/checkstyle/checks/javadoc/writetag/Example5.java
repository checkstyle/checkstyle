/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
      <property name="tag" value="@since"/>
      <property name="tagFormat" value="[1-9\.]"/>
      <property name="tagSeverity" value="error"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section -- start
// violation 3 lines below 'Javadoc tag @since=1.2'
/**
 * Some class
 * @since 1.2
 */
public class Example5 {
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
