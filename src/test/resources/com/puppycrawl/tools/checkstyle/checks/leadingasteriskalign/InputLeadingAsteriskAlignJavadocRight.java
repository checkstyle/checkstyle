/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeadingAsteriskAlign" />
  </module>
</module>
*/
// violation 1 lines above 'Leading asterisk has incorrect indentation.'

package com.puppycrawl.tools.checkstyle.checks.leadingasteriskalign;

/** Title
* This is the class level javadoc.
 * Line 2.
 */
// violation 3 lines above 'Leading asterisk has incorrect indentation.'
public class InputLeadingAsteriskAlignJavadocRight {

    /** javadoc for instance variable. */
    private int variable1;

    /**
      * Javadoc for default constructor.
     */
    // violation 2 lines above 'Leading asterisk has incorrect indentation.'
    public InputLeadingAsteriskAlignJavadocRight() {
        // empty constructor
    }/**
        * Javadoc for public method.
      */
    // violation 2 lines above 'Leading asterisk has incorrect indentation.'
    public void method() {
        System.out.println(variable1);
    }

    // violation 2 lines below 'Leading asterisk has incorrect indentation.'
    /**
   * A static inner class.
    a*/ public static class Inner {
        private Object myObj;
    }
}

/**
  Package private enum.
*/
// violation 1 lines above 'Leading asterisk has incorrect indentation.'
enum javadocEnumRight {

     /** ONE */
    ONE,

   /** TWO */
    TWO,

    /** THREE */
    THREE,
}
