/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="indentation" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/**
* Left aligned javadoc for class.
*/
public class InputJavadocLeadingAsteriskAlignIndentationZero {

  /**
  * Left aligned javadoc for field.
  */
  private int correctField;

  // violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 0.'
  /**
   * Javadoc aligned under the first asterisk of opening tag.
   */
  private int wrongField;
  // violation 2 lines above 'Leading asterisk has .* indentation .* 1, expected is 0.'

  // violation 2 lines below 'Leading asterisk has .* indentation .* 2, expected is 0.'
  /**
    * Over indented javadoc.
  */
  private void wrongMethod() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* -2, expected is 0.'
  /**
*/
  private void closingTagOnTheLeft() {}

  /**
  * Left aligned javadoc for method.
  */
  private void correctMethod() {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* 0, expected is 6.'
  // violation 3 lines below 'Leading asterisk has .* indentation .* 0, expected is 6.'
  /**   *
  * opening line asterisk defines the alignment, indentation is not applied
  */
  private void openingLineAsterisk() {}
}
