/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/**
* This is the class level javadoc.
 */
// violation 2 lines above 'Leading asterisk has incorrect indentation.'
public class InputJavadocLeadingAsteriskAlignIncorrect {
  /**
    * Javadoc for instance variable
   */
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  private int age;

  /**
  *
   */
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  private String name;

  /**
   * Javadoc for foo.
    */
  // violation 1 lines above 'Leading asterisk has incorrect indentation.'
  public void foo() {}

  /**
  */
  // violation 1 lines above 'Leading asterisk has incorrect indentation.'
  public void foo2() {}

  /**
      *
   */
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  public void foo3() {}

  /**
*
   */
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  public void foo4() {}

  /**
   * Default Constructor.
      */
  // violation 1 lines above 'Leading asterisk has incorrect indentation.'
  public InputJavadocLeadingAsteriskAlignIncorrect() {}

  /**
   * Parameterized Constructor.
*/
  // violation 1 lines above 'Leading asterisk has incorrect indentation.'
  public InputJavadocLeadingAsteriskAlignIncorrect(String a) {}

  /**
      *
    * Inner Class. */
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  // violation 2 lines above 'Leading asterisk has incorrect indentation.'
  private static class Inner {
    /**

        */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
    private Object obj;

    /**
   */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
    private Object obj2;


    /**
      * Wrong Alignment */
    // violation 1 lines above 'Leading asterisk has incorrect indentation.'
    private Object obj3;

    /**
     * @param testing
     *         Testing......
     */
    void foo(String testing) {}
  }

  private enum incorrectJavadocEnum {
    /**
     ** Duplicate leading asterisk.
     */
    // violation 2 lines above 'Duplicate leading asterisk.'
    ONE,

    /**
     *
     **/
    // violation 1 lines above 'Duplicate leading asterisk.'
    TWO,

    /**
     **This will give an error.
     */
    // violation 2 lines above 'Duplicate leading asterisk.'
    THREE,

    /**
     *
     **/
    // violation 1 lines above 'Duplicate leading asterisk.'
    FOUR,

    /**
     **
     */
    // violation 2 lines above 'Duplicate leading asterisk.'
    FIVE,

    /**
     *
     **/
    // violation 1 lines above 'Duplicate leading asterisk.'
    SIX
  }
}
