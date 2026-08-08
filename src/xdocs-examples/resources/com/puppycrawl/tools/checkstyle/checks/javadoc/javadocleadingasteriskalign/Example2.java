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
// xdoc section - start

/**
* Javadoc for class.
 */ // violation '1, expected is 0.'
public class Example2 {

  /**
    * Javadoc for instance variable, over-indented. // violation '2, expected is 0.'
   */ // violation '1, expected is 0.'
  private int wrongIndentField;

  /**
   * Javadoc for instance variable, correctly aligned. // violation '1, expected is 0.'
   */ // violation '1, expected is 0.'
  private int goodIndentField;

  /**
  *  Javadoc for method, under-indented.
  */
  private void wrongIndentMethod() {}

  /**
   * Javadoc for method, correctly aligned. // violation '1, expected is 0.'
   */ // violation '1, expected is 0.'
  private void goodIndentMethod() {}

  /**
   Javadoc for constructor, missing leading asterisk alignment on closing tag.
*/ // violation '-2, expected is 0.'
  private Example2() {}

  /**
   * Javadoc for constructor, correctly aligned. // violation '1, expected is 0.'
   */ // violation '1, expected is 0.'
  public Example2(int value) {}

  private enum indentedEnum {

    /**
    *  Wrong alignment for enum constant.
     */ // violation '1, expected is 0.'
    WRONG,

    /**
     * Correct alignment for enum constant. // violation '1, expected is 0.'
     */ // violation '1, expected is 0.'
    GOOD
  }
}
// xdoc section - end
