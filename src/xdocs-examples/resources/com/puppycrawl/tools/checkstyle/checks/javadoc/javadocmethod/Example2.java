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
  public int m1(int p1) { return p1; }
  // 1 violations above:
  //    '@return tag should be present'
  // ok, No missing param tag violation

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) { return p1; }
  // ok, only public methods are checked

  /** */
  void m3(int p1) {}

}
// xdoc section -- end
