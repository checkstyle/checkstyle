/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocUtilizingTrailingSpace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

// xdoc section - start
class Example1 {

  // violation 2 lines below 'Line under-utilized (31/80). Words from below could be moved up'
  /**
   * The company returned value
   * is invalid.
   */
  public void shortLine() { }

  // violation 2 lines below 'Line is longer than 80 characters (found 86)'
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

  // violation 2 lines below 'Line is longer than 80 characters (found 85)'
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

  // ok, a line that has a URL is not reported as too long
  /** See https://example.com/documentation/company-registry/api/v2/status-endpoints/details */
  public void longUrl() { }
}
// xdoc section - end
