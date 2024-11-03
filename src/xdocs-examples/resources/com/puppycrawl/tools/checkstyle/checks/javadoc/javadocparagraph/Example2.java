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
// violation 7 lines above 'tag should be placed immediately before the first word'
// violation 5 lines above 'tag should be placed immediately before the first word'
public class Example2 {
  // 2 violations 6 lines below:
  //  'tag should be placed immediately before the first word'
  //  '<p> tag should not precede HTML block-tag '<pre>''
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

  // 2 violations 4 lines below:
  //  'tag should be placed immediately before the first word'
  //  'Redundant <p> tag'
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
