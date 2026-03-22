/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="tabWidth" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/** Title
 * Javadoc for class
 */
public class InputJavadocLeadingAsteriskAlignCorrect {

  /** javadoc for instance variable. */
  private int a;

  /** */
  private int b;

  /***/
  private String str;

  /** */
  private String str1;

  /**
    This javadoc is allowed because there is no leading asterisk.
   */
  public InputJavadocLeadingAsteriskAlignCorrect() {}

  /***
   * @param a testing....
   */
  public InputJavadocLeadingAsteriskAlignCorrect(int a) {}

  /*************************************************
   *** @param str testing.....
   **********************************/
  public InputJavadocLeadingAsteriskAlignCorrect(String str) {}

  /** * */
  private String str2;

  /****/
  private String str3;

  /**
   * This method does nothing.
   */
  private void foo() {}

  /**
   * Javadoc for foo2
 This is allowed */
  private void foo2() {
    // foo2 code goes here
  } /**
     * This is also allowed
     */
  public void foo3() {}

  /**
   * Javadoc for enum
   * */
  private enum correctJavadocEnum {
    /**

     */
    ONE,

    /**
Testing
     */
    TWO,

    /** This is allowed

     */
    THREE,

    /**
     * Allowed. */
    FOUR,

    /**

     */
    FIVE
  }
}
