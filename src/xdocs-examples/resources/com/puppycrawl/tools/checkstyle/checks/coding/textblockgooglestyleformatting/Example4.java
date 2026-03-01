/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// xdoc section -- start
public class Example4 {

  public String testMethod1() {

    final String simpleScriptViolate =
      """
          s
           """; // violation 'text-block quotes are not vertically aligned'
    final String simpleScriptCorrect =
      """
          s
      """;

    // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    getData(
      1,
        """
        this is a multi-line message
           """); // violation 'Text-block quotes are not vertically aligned'
    getData(
      1,
        """
        this is a multi-line message
        """); // violation 2 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'

    return
        """
    this is sample text
      """; // violation 'text-block quotes are not vertically aligned'
  } // violation 2 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
  public void getData(int num, String text) {}
}
// xdoc section -- end
