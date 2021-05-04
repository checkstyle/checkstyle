/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeadingAsteriskAlign">
        <property name="option" value="LEFT" />
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.leadingasteriskalign;

// xdoc section -- start
/** Title
* This is the class level javadoc.
 * Line 2. // violation
 */
public class Example4 {

  /** javadoc for instance variable. */
  private int variable1;

  /**
    * Javadoc for default constructor. // violation
   */
  public Example4() {
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
*/
enum javadocEnumLeftExample {

  /**
   ** ONE // violation, consecutive leading asterisk.
   */
  ONE,

  /**
   TWO
    */ // violation
  // Explanation: leading asterisk should be aligned with first asterisk of the javadoc starting comment.
  TWO,

  /**
   THREE
   */ // violation
  // Explanation: leading asterisk should be aligned with first asterisk of the javadoc starting comment.
  THREE,

  /**
  * FOUR
  */
  FOUR,

  /**
  /  * testing
  * testing */
  FIVE,

  /**
    SIX
  */
  SIX,
}
// xdoc section -- end
