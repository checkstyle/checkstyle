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
 * Some class
 * @since 1.1-beta
 */
public class Example2 {

  /**
   * some doc
   * @todo remove
   * @since 1.1-beta
   */
  void testMethod1() {}
  // violation 4 lines above 'Block tag 'todo' is matched illegal pattern'
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
  // violation 4 lines above 'Block tag 'todo' is matched illegal pattern'
}
// xdoc section - end
