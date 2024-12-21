/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
      <property name="excludeScope" value="package"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// xdoc section -- start
class Example3 {
  public class testClass1 {}
  private class testClass2 {} // violation, 'Missing a Javadoc comment'
  protected class testClass3 {}
  class testClass4 {}

}
// xdoc section -- end
