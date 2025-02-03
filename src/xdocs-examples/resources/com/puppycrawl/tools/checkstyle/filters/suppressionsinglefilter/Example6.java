/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".+\.(?:png|gif|jpg|jpeg)$"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="StringLiteralEqualityCheck"/>
  <module name="JavadocMethodCheck"/>
  <module name="MagicNumberCheck"/>
  <module name="IndentationCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example6 {
  String fileName = "image.png"; // Violation, 'Hardcoded string'

  // violation below, 'Method should have a Javadoc comment'
  void loadImage() {
    System.out.println("Loading " + fileName);
  }

  // violation below, 'MagicNumberCheck (normally flagged)'
  void resizeImage() {
    int newWidth = 400, newHeight = 300;
    System.out.println("Resizing to " + newWidth + "x" + newHeight);
  }

  // Violation below, 'Non-standard indentation'
  public void incorrectIndentation() {
    System.out.println("Incorrect indentation example.");
  }
}
// xdoc section -- end
