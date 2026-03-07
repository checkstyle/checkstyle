/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocUtilizingTrailingSpace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

// xdoc section -- start
// violation 2 lines below 'Text in Javadoc line should utilize around 80 characters'
/**
 * The company returned value
 * is invalid.
 */
// violation 1 line above 'Some words from next line should be moved to this line'

// violation 2 lines below 'Line is longer than 80 characters (found 98)'
/**
 * Refer to the specific status {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
 */
// violation 1 line above

// OK - long inline tag at start of line is allowed
/**
 * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
 */

// OK - properly wrapped
/**
 * Refer to the specific status
 * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
 */
// xdoc section -- end

public class Example1 {
}
