/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="allowMissingReturnTag" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.IOException;

// xdoc section -- start
public class Example4 {

  /** */
  Example4(int x) {}
  // violation above, 'Expected @param tag for 'x'.'
  /** */
  public int m1(int p1) throws IOException {
    // violation above, 'Expected @param tag for 'p1'.'

    // ok, allowMissingReturnTag is true
    throw new IOException();
  }

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) {
    return p1;
  }

  /** */
  void m3(int p1) {}
  // violation above, 'Expected @param tag for 'p1'.'
  /**
   * {@return the foo}
   */
  public int getFoo() {

    return 0;
  }
}
// xdoc section -- end
