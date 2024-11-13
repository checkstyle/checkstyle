/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ThrowsCount">
      <property name="ignorePrivateMethods" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

// xdoc section -- start
public class Example3 {
  // violation below, 'Throws count is 5 (max allowed is 4)'
  public void myFunction() throws CloneNotSupportedException,
                                ArrayIndexOutOfBoundsException,
                                StringIndexOutOfBoundsException,
                                IllegalStateException,
                                NullPointerException {
  }

  public void myFunc() throws ArithmeticException,
                                NumberFormatException,
                                NullPointerException {
  }
  // violation below, 'Throws count is 5 (max allowed is 4)'
  private void privateFunc() throws CloneNotSupportedException,
                                ClassNotFoundException,
                                IllegalAccessException,
                                ArithmeticException,
                                ClassCastException {
  }

  private void func() throws IllegalStateException,
                                NullPointerException {
  }
}
// xdoc section -- end
