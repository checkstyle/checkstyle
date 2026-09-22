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
   * Refer to the specific status {@link com.wide.bundles.exceeds.limit.CompanyStatus}
   */
  public void longLine() { }

  // ok, long inline tag at start of line is allowed
  /**
   * {@link com.very.wide.bundles.name.that.exceeds.limit.CompanyStatus}
   */
  public void longTagAtStart() { }

  // ok, properly wrapped
  /**
   * Refer to the specific status
   * {@link com.very.wide.bundles.name.that.exceeds.limit.CompanyStatus}
   */
  public void properlyWrapped() { }

  // ok, line is under 100 characters
  /**
   * @return the status of the company, which is defined by the registry of companies
   */
  public String blockTagTooLong() { return ""; }

  // ok, the content of a pre element is ignored
  /**
   * Example:
   * <pre>
   * CompanyStatus status = new CompanyStatusFactory().createStatus(companyNumber);
   * </pre>
   */
  public void preElementContent() { }

  // ok, line is under 100 characters
  /** See https://example.com/documentation/company-registry/api/v2/status-endpoints/details */
  public void longUrl() { }
}
// xdoc section - end
