/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocEndCommentDelimiter"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

// xdoc section - start
public class Example1 {

  /**
   * Valid Javadoc.
   */
  public void valid1() { }

  /** Valid single-line Javadoc. */
  public void valid2() { }

  // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
  /**
   * Invalid Javadoc.
   **/
  public void invalid1() { }

  // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
  /** Invalid single-line Javadoc. **/
  public void invalid2() { }
}
// xdoc section - end
