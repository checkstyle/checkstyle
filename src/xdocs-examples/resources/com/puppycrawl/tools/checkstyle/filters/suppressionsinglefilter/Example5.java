/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".+\.(?:jar|zip|war|class|tar|bin)$"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="NeedBracesCheck"/>
  <module name="VisibilityModifierCheck"/>
  <module name="IndentationCheck"/>
  <module name="MagicNumberCheck"/>
  <module name="JavadocMethodCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example5 {
  // violation below, 'Single-line if statement without braces'
  void checkLength(String text) {
    if (text.length() > 10) System.out.println("Long text");
  }

  // violation below, 'Class members should be private (normally flagged)'
  int errorCode = 404;

  // violation below, 'Non-standard indentation'
  public void incorrectIndentation() {
    System.out.println("Incorrect indentation example.");
  }
}
// xdoc section -- end
