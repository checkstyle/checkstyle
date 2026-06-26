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

// xdoc section -- start

class Example2 {        // violation, 'Missing a Javadoc comment'
  /** Javadoc. */
  public class A {}

  private class B {}    // violation, 'Missing a Javadoc comment'

  protected class C {}  // violation, 'Missing a Javadoc comment'

  class D {}            // violation, 'Missing a Javadoc comment'
  /** Javadoc. */
  @SpringBootApplication
  private class App {}
  /** Javadoc. */
  @Configuration
  private class Config {}
  /** Javadoc. */
  private class E {}
}
// xdoc section -- end
