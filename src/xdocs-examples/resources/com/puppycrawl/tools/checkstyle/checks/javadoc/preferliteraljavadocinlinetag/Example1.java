/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PreferLiteralJavadocInlineTag"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;
// xdoc section - start
public class Example1 {

  // 2 violations 4 lines below:
  //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
  //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
  /**
    * Type parameter is &lt;E&gt; here.
    */
  public void badMethod() {
  }

  /**
    * Type parameter is {@literal <E>} here.
    */
  public void goodMethod() {
  }

  // violation 4 lines below """Prefer Javadoc inline
  // tag '{@literal >}' over '&gt;'."""
  /**
   * <p>
   *    &gt; is the greater than sign.
   * </p>
   */
  public void gtEntityAtStart() {
  }

  /**
   * Content inside pre blocks is allowed:
   * <pre>
   * <code>sample</code>;
   * &lt;a href="#method"&gt;link&lt;/a&gt;
   * </pre>
   */
  public void insidePreBlock() { }

  /**
   * Content inside inline tags is allowed:
   * Literal: {@literal <code>&lt;T&gt;</code>}
   * Snippet: {@snippet : <code>&lt;T&gt;</code>}
   */
  public void insideInlineTags() { }
}
// xdoc section - end
