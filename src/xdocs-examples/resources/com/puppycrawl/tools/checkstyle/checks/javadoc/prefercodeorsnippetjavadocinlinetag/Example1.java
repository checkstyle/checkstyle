/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PreferCodeOrSnippetJavadocInlineTag"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;
// xdoc section - start
public class Example1 {
  // violation 2 lines below "Use code or snippet inline tags instead of 'pre' tag."
  /**
   * <pre> This is a single line pre.</pre>
   */
  public void badMethodPre() {
  }

  /**
   * {@code This is a single line pre.}
   * {@snippet :
   *     This is a single line pre.
   * }
   */
  public void goodMethodPre() {
  }

  // violation 2 lines below 'Use code or snippet inline tags instead of 'code' tag.'
  /**
   * <code> int x = 10; </code>
   * <code>This is a left curly }</code> // ok because of unbalanced braces
   */
  public void badMethodCode() {
  }

  /**
   * {@code int x = 10;}
   * {@snippet :
   *      int x = 10;
   * }
   */
  public void goodMethodCode() {
  }

  // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
  /**
   * <pre>
   *      <code>Nested code in pre tag.</code>
   * </pre>
   * <pre>{@code  // ok the inside text started with '*'
   * /**
   *   * This is a javadoc inside javadoc start with star.
   *   * /
   * }</pre>
   */
  public void badMethod1() {
  }

}
// xdoc section - end
