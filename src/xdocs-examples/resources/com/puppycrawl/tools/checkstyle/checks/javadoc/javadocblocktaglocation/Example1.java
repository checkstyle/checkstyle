/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

// xdoc section -- start
class Example1 {
  /**
   * Escaped tag &#64;version (OK)
   * Plain text with {@code @see} (OK)
   * A @custom tag (OK)
   * Some text with misplaced @version tag
   */
  void foo1() {}
  // violation 3 lines above 'block tag '@version' should be placed at the beginning'
  /**
   * Another misplaced tag: text @noinspection here
   */
  void foo2() {}

  /**
   * @return properly placed tag (OK)
   */
  int foo3() {
    return 0;
  }
}
// xdoc section -- end
