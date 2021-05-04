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
/* Title
* This is a block comment.
 * Line 2. // violation
*/
public class Example2 {

    /* block comment for instance variable. */
    private int variable1;

    /*
   * block comment for default constructor. // violation
  */ // violation
    public Example2() {
        // empty constructor
    }/*
      * block comment for public method. // violation
     */
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
 */ // violation
enum commentEnumLeft1 {

    /* ONE */
    ONE,

    /*
      TWO
       */ // violation
    TWO,
}
// xdoc section -- end
