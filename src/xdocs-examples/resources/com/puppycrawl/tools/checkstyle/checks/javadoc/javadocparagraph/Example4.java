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
// violation 4 lines below '<p> tag should not precede HTML block-tag '<h1>''
/**
 * No tag (ok).
 *
 * <p><h1>P tag is preceding block-tag h1, not allowed</h1>
 *
 * <p><b>P tag is preceding an inline-tag b, this is allowed</b>
 */
public class Example4 {

  // 2 violations 6 lines below:
  //  'tag should be placed immediately before the first word'
  //  '<p> tag should not precede HTML block-tag '<ul>''
  /**
   * No tag (ok).
   *
   * <p>
   * <ul>
   * <li>item 1</li>
   * <li>item 2</li>
   * <li>item 3</li>
   * </ul>
   *
   * <p>
   * <pre>block-tag is preceded by P tag, not allowed.</pre>
   */
  // 2 violations 3 lines above:
  //  'tag should be placed immediately before the first word'
  //  '<p> tag should not precede HTML block-tag '<pre>''
  void foo1() {}

  /**
   * No tag (ok).
   *
   * <table>
   * <tbody>
   * <p>
   * <tr>
   * nested paragraph preceding block tag, this allowed.
   * </tr>
   * </tbody>
   * </table>
   */
  void foo2() {}
}
// xdoc section -- end
