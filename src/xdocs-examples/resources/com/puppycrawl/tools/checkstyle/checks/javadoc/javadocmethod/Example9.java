/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="ignoreMethodsWithImplementation" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.IOException;

// xdoc section - start
public class Example9 {

  /** */
  Example9(int x) {}
  // violation above 'Expected @param tag for 'x'.'
  /** */
  public int m1(int p1) throws IOException {
    // ok, Javadoc requirements are ignored
    // because the method has
    // an implementation
    throw new IOException();
  }

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) {
    return p1;
  }
  // ok, Javadoc requirements are ignored
  /** */
  void m3(int p1) {}
  // ok, Javadoc requirements are ignored
  /**
   * {@return the foo}
   */
  public int getFoo() {
    // ok, Javadoc requirements are ignored
    return 0;
  }
}
// xdoc section - end
