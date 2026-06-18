/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride">
      <property name="javaFiveCompatibility"
                value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

// xdoc section -- start
class Example2 extends ParentClass1 {

  /** {@inheritDoc} */
  @Override
  public void test1() {}

  /** {@inheritDoc} */
  // ok, javaFiveCompatibility is true

  public void test2() {}

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  private void test3() {}

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  public static void test4() {}

  /** {@inheritDoc} */
  // ok, javaFiveCompatibility is true

  public boolean equals(Object o) {
    return o == this;
  }
}

class Example2Impl implements InterfaceB {
  /** {@inheritDoc} */
  // ok, javaFiveCompatibility is true

  public void test() {}
}
// xdoc section -- end
