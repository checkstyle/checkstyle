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
  /** Javadoc.*/
  @SpringBootApplication
  private class Application { } // ok

  @Configuration
  private class DatabaseConfiguration { } // ok

  private class MissingDoc { } // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
