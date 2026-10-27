/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// xdoc section - start
/** Documented. */
public class Example2 {
  public class A {} // violation 'Missing a Javadoc comment for 'A'.'

  private class B {} // violation 'Missing a Javadoc comment for 'B'.'

  protected class C {} // violation 'Missing a Javadoc comment for 'C'.'
  /** Javadoc. */
  class D {}

  @SpringBootApplication // violation 'Missing a Javadoc comment for 'App'.'
  private class App {}

  @Configuration // violation 'Missing a Javadoc comment for 'Config'.'
  private class Config {}
  /** Javadoc. */
  private class E {}

  public interface F {} // violation 'Missing a Javadoc comment for 'F'.'
  /** Javadoc. */
  public interface G {}
}
// xdoc section - end
