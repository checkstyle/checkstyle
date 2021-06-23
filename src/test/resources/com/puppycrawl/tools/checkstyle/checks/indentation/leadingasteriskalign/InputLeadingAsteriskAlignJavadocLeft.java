package com.puppycrawl.tools.checkstyle.checks.indentation.leadingasteriskalign;

/* Config:
* option = "LEFT"
* tabSize = "4"
*/
/** Title
* This is the class level javadoc.
 * Line 2.
*/
public class InputLeadingAsteriskAlignJavadocLeft {

    /** javadoc for instance variable. */
    private int variable1;

    /**
      * Javadoc for default constructor.
      */
    public InputLeadingAsteriskAlignJavadocLeft() {
        // empty constructor
    }/**
     * Javadoc for public method.
     */
    public void method() {
        System.out.println(variable1);
    }

    /**
   * A static inner class.
    a*/ public static class Inner {
        private Object myObj;
    }
}

/**
  Package private enum.
 */
enum javadocEnumLeft {

     /** ONE */
    ONE,

   /** TWO */
    TWO,

    /** THREE */
    THREE,
}
