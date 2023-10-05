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

  // violation below 'Javadoc content should start from the next line'
  /** Text
   * spans several lines.
   */
  private int field1;

  /**
   * This comment is OK because it starts from the second line.
   */
  private int field12;

  /** This comment is OK because it is on the single-line. */
  private int field3;

}
// xdoc section -- end
