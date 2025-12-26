/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

class ParentClass1{
  public void test1(){}

  public void test2(){}

}
// xdoc section -- start
class Example1 extends ParentClass1 {

  /** {@inheritDoc} */
  @Override
  public void test1() {

  }

  /** {@inheritDoc} */
  public void test2() { // violation, 'include @java.lang.Override'

  }

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  private void test3() {

  }

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  public static void test4() {

  }

  // No javadoc, no @Override needed
  public void test5() {

  }

  // Javadoc present but no {@inheritDoc}, no @Override needed
  /**
   * This is a sample javadoc.
   */
  public void test6() {

  }
}
// xdoc section -- end
