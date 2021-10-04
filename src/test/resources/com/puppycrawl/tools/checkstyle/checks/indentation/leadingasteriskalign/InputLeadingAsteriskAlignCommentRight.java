package com.puppycrawl.tools.checkstyle.checks.indentation.leadingasteriskalign;

/* Config:
 * option = "RIGHT"
 * tabSize = "4"
 */
/* Title
* This is a block comment.
 * Line 2.
*/
public class InputLeadingAsteriskAlignCommentRight {

    /* block comment for instance variable. */
    private int variable1;

    /*
      * block comment for default constructor.
      */
    public InputLeadingAsteriskAlignCommentRight() {
        // empty constructor
    }/*
     * block comment for public method.
     */
    public void method() {
        System.out.println(variable1);
    }

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

   /* TWO */
    TWO,

    /* THREE */
    THREE,
}
