/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example1 {

  /** */
  Example1(int x) {}
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
  // violation 2 lines above '@return tag should be present'

  /** */
  void m3(int p1) {}
  // violation above, 'Expected @param tag for 'p1''
}
// xdoc section -- end
