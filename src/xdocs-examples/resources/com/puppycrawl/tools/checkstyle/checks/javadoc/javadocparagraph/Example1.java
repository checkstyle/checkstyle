/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
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
 * New line after tag (ok).
 *
 * <p> Whitespace after tag (violation).
 *
 */
// violation 3 lines above 'tag should be placed immediately before the first word'

public class Example1 {
  // violation 4 lines below '<p> tag should not precede HTML block-tag '<ul>''
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
  // violation 2 lines below 'Redundant <p> tag'
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
