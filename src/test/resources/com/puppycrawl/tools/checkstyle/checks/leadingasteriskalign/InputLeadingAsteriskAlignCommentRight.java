/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeadingAsteriskAlign" />
  </module>
</module>
*/
// violation 1 lines above 'Leading asterisk has incorrect indentation.'

package com.puppycrawl.tools.checkstyle.checks.leadingasteriskalign;

/* Title
* This is a block comment.
 * Line 2.
 */
// violation 3 lines above 'Leading asterisk has incorrect indentation.'
public class InputLeadingAsteriskAlignCommentRight {

    /* block comment for instance variable. */
    private int variable1;

    /*
      * block comment for default constructor.
     */
    // violation 2 lines above 'Leading asterisk has incorrect indentation.'
    public InputLeadingAsteriskAlignCommentRight() {
        // empty constructor
    }/*
      * block comment for public method.
     */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
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
enum commentEnumRight {

    /* ONE */
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
}
