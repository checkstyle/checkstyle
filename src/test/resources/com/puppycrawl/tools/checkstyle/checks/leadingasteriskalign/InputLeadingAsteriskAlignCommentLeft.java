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

/* Title
* This is a block comment.
 * Line 2.
*/
// violation 2 lines above 'Leading asterisk has incorrect indentation.'
public class InputLeadingAsteriskAlignCommentLeft {

    /* block comment for instance variable. */
    private int variable1;

    /*
      * block comment for default constructor.
    */
    // violation 2 lines above 'Leading asterisk has incorrect indentation.'
    public InputLeadingAsteriskAlignCommentLeft() {
        // empty constructor
    }/*
     * block comment for public method.
     */
    public void method() {
        System.out.println(variable1);
    }

    // violation 2 lines below 'Leading asterisk has incorrect indentation.'
    /*
   * A static inner class.
    a*/ public static class Inner {
        private Object myObj;
    }
}

/*
  Package private enum.
 */
// violation 1 lines above 'Leading asterisk has incorrect indentation.'
enum commentEnumLeft {

    /*
 **ONE
    */
    // violation 2 lines above 'Duplicate leading asterisk found.'
    ONE,

    /*
      TWO
      */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
    TWO,

    /*
      THREE
   */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
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
