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
 * No tag
 *
 * <p>Tag immediately before the text
 * <p>No blank line before the tag
 *
 * <p>
 * New line after tag
 *
 * <p> Whitespace after tag
 *
 * <p><b>p tag before inline tag B, this is ok</b></p>
 */
// violation 4 lines above 'tag should be placed immediately before the first word'

public class Example1 {


  // violation 4 lines below '<p> tag should not precede HTML block-tag '<pre>''
  /**
   * No tag
   *
   * <p>
   * <pre>item 1</pre>
   *
   * <table>
   * <tbody>
   * <p>
   * <tr>
   * nested paragraph preceding block tag, this is ok.
   * </tr>
   * </tbody>
   * </table>
   */

  void foo1() {}


  // violation 2 lines below 'Redundant <p> tag'
  /**
   * <p>
   * Checks whether a redundant tag is present
   * </p>
   */

  void foo2() {}

  // violation 3 lines below 'Empty line should be followed by <p> tag'
  /**
   * Double newline.
   *
   * Some Paragraph.
   */
  void foo3() {}
}
// xdoc section -- end
