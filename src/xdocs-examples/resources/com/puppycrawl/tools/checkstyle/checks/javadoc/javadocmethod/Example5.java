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

import java.io.IOException;

// xdoc section -- start
public class Example5 {

  /** */
  Example5(int x) {}
  // violation above, 'Expected @param tag for 'x'.'
  /** */
  public int m1(int p1) throws IOException {
    // 2 violations above:
    //    '@return tag should be present and have description.'
    //    'Expected @param tag for 'p1'.'
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
    // violation above, '@return tag should be present and have description.'
    return 0;
  }
}
// xdoc section -- end
