/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocUtilizingTrailingSpace">
      <property name="lineLimit" value="100"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

// xdoc section - start
class Example2 {

  // violation 2 lines below 'Line under-utilized (31/100). Words from below could be moved up'
  /**
   * The company returned value
   * is invalid.
   */
  public void shortLine() { }

  // ok, line is under 100 characters
  /**
   * Refer to the specific status {@link com.long.package.exceeds.limit.CompanyStatus}
   */
  public void longLine() { }

  // ok, long inline tag at start of line is allowed
  /**
   * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
   */
  public void longTagAtStart() { }

  // ok, properly wrapped
  /**
   * Refer to the specific status
   * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
   */
  public void properlyWrapped() { }
}
// xdoc section - end
