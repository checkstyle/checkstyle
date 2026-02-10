/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PreferJavadocInlineTags"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

// xdoc section -- start
public class Example1 {

  // violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}''
  /**
   * Returns <code>true</code> if valid.
   */
  public boolean isValid() { return true; }

  // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}''
  /**
   * See <a href="#validate">validate</a>.
   */
  public void method() { }

  // 2 violations 4 lines below:
  //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
  //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
  /**
   * Use &lt;T&gt; for generic types.
   */
  public void generics() { }

  /**
   * Correct: Returns {@code true}.
   */
  public void correctUsage() { }

  /**
   * External links are allowed: <a href="https://example.com">link</a>.
   */
  public void externalLink() { }

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
   * Entities: {@code &lt;T&gt;}
   * Literal: {@literal <a href="#">link</a>}
   */
  public void insideInlineTags() { }
}
// xdoc section -- end
