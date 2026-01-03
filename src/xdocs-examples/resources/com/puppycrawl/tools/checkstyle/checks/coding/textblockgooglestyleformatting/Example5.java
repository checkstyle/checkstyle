/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// xdoc section -- start
public class Example5 {

  public String testMethod1() {

    // violation 3 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    final String simpleScriptViolate =
      """
    s
      """;
    final String simpleScriptCorrect =
      """
          s
      """;

    // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    getData(
      1,
        """
      this is a multi-line message
        """);
    getData(
      1,
        """
        this is a multi-line message
        """);

    return
      """
        this is sample text
      """;
  }
  public void getData(int num, String text) {}
}
// xdoc section -- end
