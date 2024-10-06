/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 6 lines below '<p> tag should not precede HTML block-tag '<h1>''
// violation 9 lines below '<p> tag should not precede HTML block-tag '<pre>''
// violation 11 lines below '<p> tag should not precede HTML block-tag '<ul>''
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
public class Example3 {
}
// xdoc section -- end
