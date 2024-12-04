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

// xdoc section -- start
public class Example4 {

  /**
   *
   */
  Example4(int x) { // violation, param tag missing for x
  }

  /**
   *
   */
  public int testMethod1(int p1) {
    return p1; // violation 1 lines above 'Expected @param tag for 'p1''
  }

  /**
   *
   * @param p1 The first number
   */
  @Deprecated
  private int testMethod2(int p1) {
    return p1;
  }

  /**
   *
   */
  void testMethdod3(int p1) { // violation, param tag missing for p1
  }
}
// xdoc section -- end
