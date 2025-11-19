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
    final String simpleScript1Violate = """
        contents of the text block
      """; // violation 'Text-block quotes are not vertically aligned'
    final String simpleScript1Correct =
        """
        contents of the text block
        """;

    final String simpleScript2Violate = simpleScript1Violate +
        // violation below 'quotes (""") of text-block must be on the new line'
        simpleScript1Violate + """
        this is simple script
        """; // violation 'Text-block quotes are not vertically aligned'
    final String simpleScript2Correct = simpleScript1Violate +
        simpleScript1Violate +
            """
            contents of the text block
            """;

    getData(
        // violation below 'quotes (""") of text-block must be on the new line'
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
}
// xdoc section -- end
