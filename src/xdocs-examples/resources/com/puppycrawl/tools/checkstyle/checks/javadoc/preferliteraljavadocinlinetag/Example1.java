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

  // 5 violations 7 lines below:
  //  'Prefer literal or code javadoc inline tag over '&lt;'.'
  //  'Prefer literal or code javadoc inline tag over '&gt;'.'
  //  'Prefer literal or code javadoc inline tag over '&amp;'.'
  //  'Prefer literal or code javadoc inline tag over '&quot;'.'
  //  'Prefer literal or code javadoc inline tag over '&apos;'.'
  /**
   * Entities are : &lt;, &gt;, &amp;, &quot;, &apos;.
   */
  public void badMethod() {
  }

  /**
   * Entities are : {@literal <}, {@literal >},
   * {@literal &}, {@literal "}, {@literal '}.
   */
  public void goodMethod() {
  }

  // violation 4 lines below """Prefer literal or code javadoc
  // inline tag over '&gt;'."""
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
   * {@snippet :
   *    &lt;T&gt;
   * }
   */
  public void insideInlineTags() { }
}
// xdoc section - end
