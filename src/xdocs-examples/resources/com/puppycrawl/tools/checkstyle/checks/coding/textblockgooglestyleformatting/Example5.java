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
    // violation 2 lines below 'Each line of text in the text block must be indented'
    final String simpleScriptViolate =
            """
         s
            """;
    final String simpleScriptCorrect =
            """
            s
            """;
    // violation 3 lines below 'Each line of text in the text block must be indented'
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
      """; // violation 'text-block quotes are not vertically aligned'
  }
  public void getData(int num, String text) {}
}
// xdoc section -- end
