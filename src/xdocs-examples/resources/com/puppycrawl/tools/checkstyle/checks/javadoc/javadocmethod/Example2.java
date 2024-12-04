/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="accessModifiers" value="public"/>
      <property name="allowMissingParamTags" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example2 {
  /** */
  Example2(int x) {}

  /** */
  public int testMethod1(int p1) {
    // violation above, '@return tag should be present'
    return p1;
  }
  /**
   * @param p1 The first number
   */
  @Deprecated
  private int testMethod2(int p1) {
    return p1;
  }
  /** */
  void testMethdod3(int p1) {}

}
// xdoc section -- end
