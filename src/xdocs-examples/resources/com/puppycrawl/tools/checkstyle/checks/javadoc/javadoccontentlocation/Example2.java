/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocContentLocation">
      <property name="location" value="first_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

// xdoc section -- start
class Example2 {

  /** This is a multi-line Javadoc.
   * Additional description.
   */
  private int field1;
  // violation below 'Javadoc content should start from the same line.'
  /**
   * This is another multi-line Javadoc.
   */
  private int field2;

  /** This is a single-line Javadoc. */
  private int field3;

}
// xdoc section -- end
