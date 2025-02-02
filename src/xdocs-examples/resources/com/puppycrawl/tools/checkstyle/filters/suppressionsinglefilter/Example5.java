/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".+\.(?:jar|zip|war|class|tar|bin)$"/>
    <property name="checks" value=".*"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;

// xdoc section -- start
public class Example5 {
  // Violation below, 'Single-line if statement without braces'
  void checkLength(String text) {
    if (text.length() > 10) System.out.println("Long text");
  }

  // Violation below, 'Class members should be private (normally flagged)'
  int errorCode = 404;

  // Violation below, 'Non-standard indentation'
  public void incorrectIndentation() {
    System.out.println("Incorrect indentation example.");
  }
}
// xdoc section -- end
