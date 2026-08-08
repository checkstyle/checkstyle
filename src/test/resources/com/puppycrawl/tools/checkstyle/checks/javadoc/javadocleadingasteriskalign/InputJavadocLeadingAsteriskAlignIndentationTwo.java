/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="indentation" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/**
  * Javadoc indented by two columns from the slash.
  */
public class InputJavadocLeadingAsteriskAlignIndentationTwo {

  // violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 2.'
  /**
   * Javadoc aligned under the first asterisk of opening tag.
   */
  private int wrongField;
  // violation 2 lines above 'Leading asterisk has .* indentation .* 1, expected is 2.'

  /**
    * Correctly indented javadoc.
    */
  private void correctMethod() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 5, expected is 2.'
  /**
       * Over indented javadoc.
    */
  private void wrongMethod() {}
}
