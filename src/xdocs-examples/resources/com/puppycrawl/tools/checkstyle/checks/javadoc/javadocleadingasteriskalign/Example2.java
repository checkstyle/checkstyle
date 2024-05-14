/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

// xdoc section -- start
/** Title
* Javadoc for class // violation
    */ // violation
public class Example2 {
  /**
    *  Javadoc for instance variable. // violation
    */ // violation
  private String name;

  /**
  *  Javadoc for method. // violation
  */ // violation
  private void foo() {}

  /**
   Javadoc for Constructor.
*/ // violation
  private Example2() {}

  /**
    * Javadoc for enum. // violation
   */
  private enum incorrectJavadocEnum {

    /**
    *  // violation
     */
    ONE,

    /**
        * Incorrect indentation for leading asterisk. */ // violation
    TWO,

    /**
 *    // violation
     */
    THREE
  }
}
// xdoc section -- end
