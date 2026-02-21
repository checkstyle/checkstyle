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
        // violation 2 lines below 'quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        simpleScript1Violate + """
        this is simple script
        """; // violation 'Text-block quotes are not vertically aligned'
    final String simpleScript2Correct = simpleScript1Violate +
        simpleScript1Violate +
            """
            contents of the text block
            """;

    getData(
        // violation 2 lines below 'quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        1, """
           this is a multi-line message
           """); // violation above lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    getData(
        1,
           """
           this is a multi-line message
           """); // violation above lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    return """
        this is sample text
        """; // violation 'Text-block quotes are not vertically aligned'
  } // violation 2 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
  public void getData(String text, int num) {}
  public void getData(int num, String text) {}
}
// xdoc section -- end
