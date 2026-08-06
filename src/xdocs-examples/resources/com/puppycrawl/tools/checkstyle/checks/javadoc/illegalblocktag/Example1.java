/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

// xdoc section - start
/**
 * some doc
 * @todo something
 */
public class Example1 {
  /**
   * some doc
   * @since 1.1-beta
   */
  void testMethod1() {}
}
// xdoc section - end
