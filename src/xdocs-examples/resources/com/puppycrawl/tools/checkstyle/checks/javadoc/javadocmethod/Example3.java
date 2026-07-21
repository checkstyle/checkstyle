/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="accessModifiers" value="private, package"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.IOException;

// xdoc section -- start
public class Example3 {

  /** */
  Example3(int x) {}

  /** */
  public int m1(int p1) throws IOException {

    // ok, only private and package access modifiers are checked.

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

  /**
   * {@return the foo}
   */
  public int getFoo() {

    return 0;
  }
}
// xdoc section -- end
