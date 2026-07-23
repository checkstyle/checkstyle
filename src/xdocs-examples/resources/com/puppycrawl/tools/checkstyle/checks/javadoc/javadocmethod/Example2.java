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

import java.io.IOException;

// xdoc section -- start
public class Example2 {

  /** */
  Example2(int x) {}

  /** */
  public int m1(int p1) throws IOException {
    // violation above '@return tag should be present and have description.'

    // ok, allowMissingParamTags is true
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
    // violation above '@return tag should be present and have description.'
    return 0;
  }
}
// xdoc section -- end
