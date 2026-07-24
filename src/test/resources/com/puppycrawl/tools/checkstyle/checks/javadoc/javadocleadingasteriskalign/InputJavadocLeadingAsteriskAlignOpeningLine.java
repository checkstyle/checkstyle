/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

public interface InputJavadocLeadingAsteriskAlignOpeningLine {

    // violation 5 lines below 'Leading asterisk has incorrect indentation level 6, expected is 11.'
    // violation 5 lines below 'Leading asterisk has incorrect indentation level 6, expected is 11.'
    // violation 5 lines below 'Leading asterisk has incorrect indentation level 6, expected is 11.'

    /**   *  ***
     * There is a space, then a leading asterisk, then a space,
     * then asterisks in the first line
     */
    void test();
}
