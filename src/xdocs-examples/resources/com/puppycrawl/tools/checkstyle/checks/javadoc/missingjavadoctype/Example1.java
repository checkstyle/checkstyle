/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// xdoc section - start
/** Documented. */
public class Example1 {
  public class A {} // violation 'Missing a Javadoc comment for 'A'.'

  private class B {}

  protected class C {}
  /** Javadoc. */
  class D {}

  @SpringBootApplication
  private class App {}

  @Configuration
  private class Config {}
  /** Javadoc. */
  private class E {}

  public interface F {} // violation 'Missing a Javadoc comment for 'F'.'
  /** Javadoc. */
  public interface G {}
}
// xdoc section - end
