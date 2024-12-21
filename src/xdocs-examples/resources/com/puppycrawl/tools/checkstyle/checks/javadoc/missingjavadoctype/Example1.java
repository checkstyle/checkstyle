/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// xdoc section -- start
class Example1 {
  /** Javadoc.*/
  public class testClass0 {}
  public class testClass1 {}
  private class testClass2 {}
  protected class testClass3 {}
  class testClass4 {}
}
// xdoc section -- end
