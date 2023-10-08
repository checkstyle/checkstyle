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

  /** This comment is OK because it starts on the first line.
   * There may be additional text.
   */
  private int field1;

  // violation below, 'Javadoc content should start from the same line.'
  /**
   *
   * Text.
   */
  private int field2;

  /** This single-line comment also is OK. */
  private int field3;

}
// xdoc section -- end
