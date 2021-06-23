package com.puppycrawl.tools.checkstyle.checks.indentation.leadingasteriskalign;

/* Config:
* option = "LEFT"
* tabSize = "4"
*/
/* Title
* This is a block comment.
 * Line 2.
*/
public class InputLeadingAsteriskAlignCommentLeft {

    /* block comment for instance variable. */
    private int variable1;

    /*
      * block comment for default constructor.
      */
    public InputLeadingAsteriskAlignCommentLeft() {
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
enum commentEnumLeft {

     /* ONE */
    ONE,

   /* TWO */
    TWO,

    /* THREE */
    THREE,
}
