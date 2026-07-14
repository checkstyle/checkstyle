/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SummaryJavadoc">
      <property name="period" value="。"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

// xdoc section -- start
class Example3 {
  // violation below, 'Summary javadoc is missing'
  /** */
  public String withoutJavadoc() { return ""; }

  /**
   * {@summary  }
   */
  public String withJavadoc() { return ""; }
  // violation 3 lines above 'Summary javadoc is missing'

  /**
   * {@summary This is a java doc with dot period.}
   */
  public void withPeriod() {}
  // violation 3 lines above 'Summary of Javadoc is missing an ending period'
  /**
   * {@summary This is a java doc with ideographic period。}
   */
  public void withoutPeriod() {}

  /**
   * This method returns nothing.
   */
  void withDotPeriod() {}
  // violation 4 lines above 'First sentence of Javadoc is missing an ending period'
  /**
   * This method returns nothing。
   */
  void withoutDotPeriod() {}

}
// xdoc section -- end
