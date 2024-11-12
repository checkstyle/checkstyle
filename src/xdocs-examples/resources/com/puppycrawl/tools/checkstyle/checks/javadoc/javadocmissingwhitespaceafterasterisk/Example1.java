/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMissingWhitespaceAfterAsterisk"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingwhitespaceafterasterisk;

// xdoc section -- start
/** This is valid single-line Javadoc. */
class Example1 {
  /**
   *This is invalid Javadoc.
   */ // violation above, 'Missing a whitespace after the leading asterisk'
  int invalidJavaDoc;
  /**
   * This is valid Javadoc.
   */
  void validJavaDocMethod() {
  }
  /**This is invalid single-line Javadoc. */
  // violation above, 'Missing a whitespace after the leading asterisk'
  void invalidSingleLineJavaDocMethod() {
  }
  /** This is valid single-line Javadoc. */
  void validSingleLineJavaDocMethod() {
  }
}
// xdoc section -- end
