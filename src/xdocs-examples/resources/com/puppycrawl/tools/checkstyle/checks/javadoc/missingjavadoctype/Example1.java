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

  private class B {}

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

  public interface F {} // violation, 'Missing a Javadoc comment'
  /** Javadoc. */
  public interface G {}
}
// xdoc section -- end
