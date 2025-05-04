/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="tokens" value="CTOR_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example6 {

  /** */
  Example6(int x) {}
  // violation above, 'Expected @param tag for 'x''
  /** */
  public int m1(int p1) { return p1; }
  // ok, No missing param tag violation
  // ok, No missing @return tag violation
  // only constructors are checked

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) { return p1; }
  // ok, No missing @return tag violation

  /** */
  void m3(int p1) {}

}
// xdoc section -- end
