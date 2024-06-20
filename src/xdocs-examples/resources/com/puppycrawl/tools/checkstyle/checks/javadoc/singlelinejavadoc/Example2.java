/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SingleLineJavadoc">
      <property name="ignoredTags" value="@inheritDoc, @see"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

// xdoc section -- start
public class Example2 {
  /** @see Math */
  public int foo() {
    return 42;
  }

  /**
   * @return 42
   */
  public int bar() {
    return 42;
  }
  //ok below, because inline tag is ignored
  /** {@link #equals(Object)} */
  public int baz() {
    return 42;
  }

  /**
   * <p>the answer to the ultimate question
   */
  public int magic() {
    return 42;
  }

  /**
   * <p>the answer to the ultimate question</p>
   */
  public int foobar() {
    return 42;
  }
}
// xdoc section -- end
