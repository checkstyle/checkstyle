/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

class ParentClass1 {
  public void test1() {}
  public void test2() {}
}

interface InterfaceB {
  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  void test();
}

// xdoc section -- start
class Example1 extends ParentClass1 {

  /** {@inheritDoc} */
  @Override
  public void test1() {}

  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  public void test2() {}

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  private void test3() {}

  /** {@inheritDoc} */
  // violation below '{@inheritDoc} tag is not valid at this location.'
  public static void test4() {}

  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  public boolean equals(Object o) {
    return o == this;
  }
}

class Example1Impl implements InterfaceB {
  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  public void test() {}
}
// xdoc section -- end
