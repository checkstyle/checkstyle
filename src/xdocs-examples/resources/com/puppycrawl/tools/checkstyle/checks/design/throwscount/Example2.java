/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ThrowsCount">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

// xdoc section -- start
public class Example2 {
  // violation below, 'Throws count is 5 (max allowed is 2)'
  public void myFunction() throws CloneNotSupportedException,
                                ArrayIndexOutOfBoundsException,
                                StringIndexOutOfBoundsException,
                                IllegalStateException,
                                NullPointerException {
  }
  // violation below, 'Throws count is 3 (max allowed is 2)'
  public void myFunc() throws ArithmeticException,
                                NumberFormatException,
                                NullPointerException {
  }

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
