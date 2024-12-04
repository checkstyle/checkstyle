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

  /**
   *
   */
  Example1(int x) { // violation, param tag missing for x
  }

  /**
   *
   */
  public int testMethod1(int p1) {  // violation, '@return tag should be present'
    return p1;  // violation 1 lines above 'Expected @param tag for 'p1''
  }

  /**
   *
   * @param p1 The first number
   */
  @Deprecated
  private int testMethod2(int p1) {
    return p1; // violation 2 lines above '@return tag should be present'
  }

  /**
   *
   */
  void testMethdod3(int p1) { // violation, param tag missing for p1
  }
}
// xdoc section -- end
