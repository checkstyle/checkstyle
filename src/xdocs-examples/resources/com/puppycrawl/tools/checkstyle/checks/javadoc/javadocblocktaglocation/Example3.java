/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation">
      <!-- default tags -->
      <property name="tags" value="author, deprecated, exception, hidden"/>
      <property name="tags" value="param, provides, return, see, serial"/>
      <property name="tags" value="serialData, serialField, since, throws"/>
      <property name="tags" value="uses, version"/>
      <!-- additional tags used in the project -->
      <property name="tags" value="noinspection"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

// xdoc section -- start
class Example3 {
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
  // violation 3 lines above 'should be placed at the beginning'
  /**
   * @return properly placed tag (OK)
   */
  int foo3() {
    return 0;
  }
}
// xdoc section -- end
