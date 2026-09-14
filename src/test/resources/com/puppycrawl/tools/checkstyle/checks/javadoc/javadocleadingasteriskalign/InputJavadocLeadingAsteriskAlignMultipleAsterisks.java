/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

public class InputJavadocLeadingAsteriskAlignMultipleAsterisks {

  /****
   ** Correctly aligned multiple asterisks.
   */
  void valid() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 2, expected is 1.'
  /****
    ** Misaligned multiple asterisks.
   */
  void invalid() {}
}
