/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="StringLiteralEquality"/>
    <module name="MagicNumber"/>
    <module name="JavadocTagContinuationIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example6 {

  // Checkstyle: Suppress StringLiteralEqualityCheck for this line
  String fileName = "image.png"; // Suppressed StringLiteralEqualityCheck here

  // Checkstyle: Suppress JavadocMethodCheck for this method
  void loadImage() {
    // Suppressed JavadocMethodCheck here
    System.out.println("Loading " + fileName);
  }

  // Checkstyle: Suppress MagicNumberCheck for this method
  void resizeImage() {
    int newWidth = 400, newHeight = 300; // Suppressed MagicNumberCheck here
    System.out.println("Resizing to " + newWidth + "x" + newHeight);
  }

  // Checkstyle: Suppress IndentationCheck for this method
  public void incorrectIndentation() {
    System.out.println("Incorrect indentation example."); // Suppressed IndentationCheck here
  }
}
// xdoc section -- end
