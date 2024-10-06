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
// violation 7 lines below '<p> tag should not precede HTML block-tag '<h1>''
// 2 violations 10 lines below:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should not precede HTML block-tag '<pre>''
/**
 * Some summary.
 *
 * <p><h1>P tag is preceding block-tag h1, not allowed</h1>
 *
 * <p><b>P tag is preceding an inline-tag b, this is allowed</b>
 *
 * <p>
 * <pre>block-tag is preceded by P tag, not allowed.</pre>
 *
 * <p>
 * <ul>
 * <li>item 1</li>
 * <li>item 2</li>
 * <li>item 3</li>
 * </ul>
 *
 * <table>
 * <tbody>
 * <p>
 * <tr>
 * nested paragraph preceding block tag, this allowed.
 * </tr>
 * </tbody>
 * </table>
 *
 */
// 2 violations 17 lines above:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should not precede HTML block-tag '<ul>''
public class Example4 {
}
// xdoc section -- end
