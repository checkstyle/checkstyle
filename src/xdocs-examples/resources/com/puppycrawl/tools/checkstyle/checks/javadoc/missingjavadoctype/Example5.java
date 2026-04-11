/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
      <property name="skipAnnotations" value="Annotation,MyClass.Annotation"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/** Documented. */
@interface Annotation { }

/** Documented. */
class MyClass {
    /** Documented. */
    @interface Annotation { }
}

// xdoc section -- start
/** Documented. */
class Example5 {
  /** Javadoc.*/
  @Annotation
  private class Class1 { } // ok

  @MyClass.Annotation
  private class Class2 { } // ok

  private class Class3 { } // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
