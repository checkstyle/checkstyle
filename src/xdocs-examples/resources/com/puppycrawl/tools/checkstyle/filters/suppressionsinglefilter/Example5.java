/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="NeedBraces"/>
    <module name="VisibilityModifier"/>
    <module name="JavadocTagContinuationIndentation"/>
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example5 {

  // Checkstyle: Suppress NeedBracesCheck for this line
  void checkLength(String text) {
    // Suppressed NeedBracesCheck here
    if (text.length() > 10) System.out.println("Long text");
  }

  // Checkstyle: Suppress VisibilityModifierCheck for this line
  int errorCode = 404; // Suppressed VisibilityModifierCheck here

  // Checkstyle: Suppress IndentationCheck for this line
  public void incorrectIndentation() {
    // Suppressed IndentationCheck here
    System.out.println("Incorrect indentation example.");
  }
}
// xdoc section -- end
