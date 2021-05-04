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

// xdoc section -- start
/**
 * Example with `tabWidth` property.
 * This example contains Tabs as well as Spaces.
 */
public class Example3 {
  /** <- Preceded with Tabs.
   * <- Preceded with Tabs & Spaces.
   */ // <- Preceded with Tabs & Spaces.
  private String name;

  /** <- Preceded with Spaces.
   * <- Preceded with Tabs.
   */ // <- Preceded with Tabs.
  private void foo() {}

  /**
    * // violation
  */ // violation
  private Example3() {}

  private enum tabsExample {
    /**
       * Incorrect indentation for leading asterisk. // violation */
    ONE,

    /**
      This javadoc is allowed because there is no leading asterisk.
     */
    TWO
  }
}
// xdoc section -- end
