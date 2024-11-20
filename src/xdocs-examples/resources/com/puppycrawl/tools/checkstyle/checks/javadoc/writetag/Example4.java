/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tag" value="@since"/>
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
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
  // violation 2 lines below 'Type Javadoc tag @since'
  /** some doc
  * @since
  */
  void testMethod1() {}

  public void testMethod2() {}
}
// xdoc section -- end
