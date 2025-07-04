/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// xdoc section -- start
public class Example2 {
  public String testMethod1() {
    // violation below 'Opening quotes (""") of text-block must be on the new line'
    final String simpleScript = """
        contents of the text block
      """; // violation 'Text-block quotes are not vertically aligned'

    final String simpleScriptCorrect =
        """
        contents of the text block
        """;

    // violation 2 lines below:
    //   'Opening quotes (""") of text-block must be on the new line'
    final String simpleScript2 = simpleScript +
        simpleScript + """
        this is simple script
        """; // violation 'Text-block quotes are not vertically aligned'

    final String simpleScript2Correct = simpleScript +
        simpleScript +
            """
            contents of the text block
            """;

    // violation 2 lines below:
    //   'Opening quotes (""") of text-block must be on the new line'
    getData(
        1, """
           this is a multi-line message
           """);

    getData(
        1,
           """
           this is a multi-line message
           """);

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    return """
        this is sample text
        """; // violation 'Text-block quotes are not vertically aligned'
  }
  public void getData(String text, int num) {}
  public void getData(int num, String text) {}
  public String getName() {
    return null;
  }
}
// xdoc section -- end
