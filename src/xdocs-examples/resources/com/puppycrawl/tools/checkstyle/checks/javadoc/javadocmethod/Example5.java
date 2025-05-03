/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="allowedAnnotations" value="Deprecated"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example5 {

  /** */
  Example5(int x) {}
  // violation above, 'Expected @param tag for 'x''
  /** */
  public int m1(int p1) { return p1; }
  // 2 violations above:
  //    '@return tag should be present'
  //    'Expected @param tag for 'p1''

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) { return p1; }
  // ok, No missing @return tag violation

  /** */
  void m3(int p1) {}
  // violation above, 'Expected @param tag for 'p1''
}
// xdoc section -- end
