/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph">
      <property name="allowNewlineParagraph" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 5 lines below '<p> tag should be preceded with an empty line'
/**
 * No tag (ok).
 *
 * <p>Tag immediately before the text (ok).
 * <p>No blank line before the tag (violation).
 *
 * <p>
 * New line after tag (violation).
 *
 * <p> Whitespace after tag (violation).
 *
 */
// violation 6 lines above 'tag should be placed immediately before the first word'
// violation 4 lines above 'tag should be placed immediately before the first word'
public class Example2 {
  // 2 violations 6 lines below:
  //  'tag should be placed immediately before the first word'
  //  '<p> tag should not precede HTML block-tag '<ul>''
  /**
   * No tag (ok).
   *
   * <p>
   * <ul>
   * <li>item 1</li>
   * </ul>
   *
   */
  void foo1() {}
  // 2 violations 4 lines below:
  //  'tag should be placed immediately before the first word'
  //  'Redundant <p> tag'
  /**
   * <p>
   * Checks whether a redundant tag is present
   * </p>
   */
  void foo2() {}
  /**
   * Double newline.
   *
   * Some Paragraph.
   */
  // violation 3 lines above 'Empty line should be followed by <p> tag'
  void foo3() {}
}
// xdoc section -- end
