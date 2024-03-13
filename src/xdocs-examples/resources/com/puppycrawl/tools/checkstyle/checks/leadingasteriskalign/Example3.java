/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.leadingasteriskalign;

// xdoc section -- start
/** Title
* This is the class level javadoc. // violation
 * Line 2.
 */
public class Example3 {

  /** javadoc for instance variable. */
  private int variable1;

  /**
    * Javadoc for default constructor. // violation
   */
  public Example3() {
    // empty constructor
  }/**
      * Javadoc for public method. // violation
   */
  public void method() {
    System.out.println(variable1);
  }


  /**
 * A static inner class. // violation
   a*/ public static class Inner {
    private Object myObj;
  }
}

/**
 Package private enum.
*/ // violation
enum javadocEnumRightExample {

  /** ONE */
  ONE,

  /**
   * TWO
   */
  TWO,

  /**
    THREE
   */
  THREE,
}
// xdoc section -- end
