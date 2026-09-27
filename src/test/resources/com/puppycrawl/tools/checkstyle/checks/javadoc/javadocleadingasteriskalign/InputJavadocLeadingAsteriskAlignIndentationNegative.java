/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="indentation" value="-2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

public class InputJavadocLeadingAsteriskAlignIndentationNegative {

    /**
  * Javadoc with asterisks two columns left of the slash.
  */
    private int correctField;

    // violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is -2.'
    /**
     * Javadoc aligned under the first asterisk of opening tag.
     */
    private int wrongField;
    // violation 2 lines above 'Leading asterisk has .* indentation .* 1, expected is -2.'
}
