/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// xdoc section -- start
/** Documented. */
public class Example1 {
  /** Javadoc. */
  public class A {}
  /** Javadoc. */
  private class B {}
  /** Javadoc. */
  protected class C {}
  /** Javadoc. */
  class D {}
  /** Javadoc. */
  @SpringBootApplication
  private class App {}
  /** Javadoc. */
  @Configuration
  private class Config {}
  /** Javadoc. */
  private class E {}
  /** Javadoc. */
  public interface F {}
  /** Javadoc. */
  public interface G {}
}
// xdoc section -- end
