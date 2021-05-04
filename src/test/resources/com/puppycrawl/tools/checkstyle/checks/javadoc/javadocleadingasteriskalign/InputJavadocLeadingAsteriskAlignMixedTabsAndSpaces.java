/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="tabSize" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/** Title
 * Javadoc for class
 */
public class InputJavadocLeadingAsteriskAlignMixedTabsAndSpaces {

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
  public InputJavadocLeadingAsteriskAlignMixedTabsAndSpaces() {}

  /***
   * @param a testing....
   */
  public InputJavadocLeadingAsteriskAlignMixedTabsAndSpaces(int a) {}

  /*************************************************
   *** @param str testing.....
   **********************************/
  public InputJavadocLeadingAsteriskAlignMixedTabsAndSpaces(String str) {}

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
    FIVE,

    /** <- Preceded with spaces only.
	   * <- Asterisk preceded with Tabs, ok
	   * <- Asterisk preceded with Spaces, ok
	   * <- Asterisk preceded with Tabs & Spaces, ok
	   */
	  SIX,

	  /** <- Preceded with spaces only & tabs.
		 * <- Asterisk preceded with Tabs, ok
		 * <- Asterisk preceded with Spaces, ok
		 * <- Asterisk preceded with Tabs & Spaces, ok
		 */
		SEVEN
  }
}
