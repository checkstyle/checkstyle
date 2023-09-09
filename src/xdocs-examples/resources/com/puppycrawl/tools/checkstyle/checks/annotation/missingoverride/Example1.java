/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

// xdoc section -- start
class Example1{
  public void test1(){}

  public void test2(){}

}

class Test extends Example1 {

  /** {@inheritDoc} */
  @Override
  public void test1() { // OK

  }

  /** {@inheritDoc} */
  public void test2() { // violation, 'include @java.lang.Override'

  }

  /** {@inheritDoc} */
  private void test3() { // violation, 'The Javadoc {@inheritDoc}.'

  }

  /** {@inheritDoc} */
  public static void test4() { // violation, 'The Javadoc {@inheritDoc}.'

  }
}
// xdoc section -- end
