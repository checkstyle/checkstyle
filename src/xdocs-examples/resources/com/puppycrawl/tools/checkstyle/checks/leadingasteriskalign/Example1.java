/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.leadingasteriskalign;
// xdoc section -- start
/* Title
* This is a block comment. // violation
 * Line 2.
 */
public class Example1 {

  /* block comment for instance variable. */
  private int variable1;

    /*
      * block comment for default constructor. // violation
       */ // violation
    public Example1() {
        // empty constructor
    }/*
      * block comment for public method.
     */ // violation
    public void method() {
        System.out.println(variable1);
    }

    /*
   * A static inner class. // violation
    a*/ public static class Inner {
        private Object myObj;
    }
}

/*
  Package private enum.
 */
enum commentEnumRightExample {

    /*
          ** ONE // violation, consecutive leading asterisks.
     */
    ONE,

    /*
     TWO
  */ // violation
    TWO,

    /*
      THREE
       */ // violation
    THREE,

    /*
     * FOUR
     */
    FOUR,

    /*
     /  * testing
     * testing */
    FIVE,

    /*
      SIX
     */
    SIX,
}
// xdoc section -- end
