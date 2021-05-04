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
 * Testing check with Tabs...
 */
public class InputJavadocLeadingAsteriskAlignTabs {

	/** <- Preceded with Tabs. */
	private int a;

	 /** <- Preceded with Tabs and Spaces.
		* <- Preceded with Tabs.
		*/ // <- Preceded with Tabs.
  private int b;

		/** <- Preceded with Tabs.
	   * <- Preceded with Tabs and Spaces.
	   */ // <- Preceded with Tabs and Spaces.
	private void foo() {}

	/** <- Preceded with Tabs.
   * <- Preceded with Spaces.
   */ // <- Preceded with Spaces.
  private void foo2() {}

   /** <- Preceded with Spaces.
		* <- Preceded with Tabs.
		*/ // <- Preceded with Tabs.
  private void foo3() {}

	private void foo4() {
		// foo2 code goes here
	} /**
		 * This is allowed.
		 */
	public InputJavadocLeadingAsteriskAlignTabs() {}

	/***
	  * @param x testing..... */ // violation
	public InputJavadocLeadingAsteriskAlignTabs(int x) {}

	/*****
	 * @param str testing...... */
	public InputJavadocLeadingAsteriskAlignTabs(String str) {}

	private enum enumWithTabs {

	  /**
		*  // violation
		  */ // violation
		ONE,

		/**
				This javadoc is allowed because there is no leading asterisk.
		 */
		TWO,

		/**
			* // violation
		*/ // violation
		THREE,

		/**
		This is allowed because there is no leading asterisk. */
		FOUR
	}
}
