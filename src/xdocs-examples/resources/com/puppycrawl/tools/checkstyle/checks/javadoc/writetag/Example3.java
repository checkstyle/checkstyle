/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tag" value="@since"/>
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section -- start
/**
* Some class
*/
public class Example3 { // violation, 'Type Javadoc comment is missing @since tag'
  /** some doc */
  void testMethod1() {} // violation, 'Type Javadoc comment is missing @since tag'

  public void testMethod2() {}
}
// xdoc section -- end
