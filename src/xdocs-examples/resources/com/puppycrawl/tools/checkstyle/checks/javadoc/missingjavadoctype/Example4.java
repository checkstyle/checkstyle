/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
      <property name="skipAnnotations"
        value="SpringBootApplication,Configuration"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/** Documented. */
@interface SpringBootApplication { }
/** Documented. */
@interface Configuration { }

// xdoc section - start
/** Documented. */
public class Example4 {
  public class A {} // violation 'Missing a Javadoc comment for 'A'.'

  private class B {} // violation 'Missing a Javadoc comment for 'B'.'

  protected class C {} // violation 'Missing a Javadoc comment for 'C'.'
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
