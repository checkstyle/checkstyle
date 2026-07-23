/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SummaryJavadoc">
      <property name="forbiddenSummaryFragments"
        value="^This method returns.*"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

// xdoc section -- start
class Example2 {
  // violation below 'Summary javadoc is missing'
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

  /**
   * {@summary This is a java doc with ideographic period。}
   */
  public void withoutPeriod() {}
  // violation 3 lines above 'Summary of Javadoc is missing an ending period'
  /**
   * This method returns nothing.
   */
  void withDotPeriod() {}
  // violation 4 lines above 'Forbidden summary fragment'
  /**
   * This method returns nothing。
   */
  void withoutDotPeriod() {}
  // violation 4 lines above 'First sentence of Javadoc is missing an ending period'
}
// xdoc section -- end
