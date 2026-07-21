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

/**
 * Some class
 *
 */
public class Example5 {
  // violation 1 lines above 'Type Javadoc comment is missing @since tag.'
  /**
   * some doc
   * @since
   */
  void testMethod1() {}
  // violation 3 lines above 'Type Javadoc tag @since must match pattern'
  /** some doc */
  public void testMethod2() {}
  // violation 1 lines above 'Type Javadoc comment is missing @since tag.'
}
// xdoc section -- end
