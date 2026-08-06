/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag">
      <property name="tag" value="@todo"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// xdoc section - start
/**
 * some doc
 * @todo remove
 */
public class Example2 {
  // violation 3 lines above 'Block tag 'todo' is matched illegal pattern'
  /**
   * some doc
   * @since 1.0
   */
  void testMethod1() {}
}
// xdoc section - end
