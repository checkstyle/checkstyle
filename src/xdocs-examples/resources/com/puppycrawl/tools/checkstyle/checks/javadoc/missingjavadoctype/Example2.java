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
class Example2 { // violation, 'Missing a Javadoc comment'
  /** Javadoc.*/
  public class testClass0 {}
  public class testClass1 {} // violation, 'Missing a Javadoc comment'
  private class testClass2 {} // violation, 'Missing a Javadoc comment'
  protected class testClass3 {} // violation, 'Missing a Javadoc comment'
  class testClass4 {} // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
