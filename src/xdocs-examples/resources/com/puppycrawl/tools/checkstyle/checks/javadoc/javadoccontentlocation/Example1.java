/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocContentLocation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

// xdoc section -- start
class Example1 {
  // violation below 'Javadoc content should start from the next line.'
  /** This is a multi-line Javadoc.
   * Additional description.
   */
  private int field1;

  /**
   * This is another multi-line Javadoc.
   */
  private int field2;

  /** This is a single-line Javadoc. */
  private int field3;

}
// xdoc section -- end
