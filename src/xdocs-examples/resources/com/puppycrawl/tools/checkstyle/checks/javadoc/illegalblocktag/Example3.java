/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF" />
      <property name="tag" value="@somesince"/>
      <property name="tagTextPattern" value="^[1-9\\.]+$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// xdoc section - start
public class Example3 {
  /**
   * some doc
   * @somesince 1.1-betta
   */
  void testMethod1() {}
  // violation 3 lines above 'Block tag 'somesince' is matched illegal pattern'
  /**
   * some doc
   * @somesince 1.2
   */
  void testMethod2() {}
}
// xdoc section - end
