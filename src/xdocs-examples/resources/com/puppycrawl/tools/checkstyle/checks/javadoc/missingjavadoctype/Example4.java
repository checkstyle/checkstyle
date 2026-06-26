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

// xdoc section -- start
/** Documented. */
class Example4 {
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

  private class E {} // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
