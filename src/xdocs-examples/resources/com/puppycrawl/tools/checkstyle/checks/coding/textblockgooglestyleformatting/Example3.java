/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;
// xdoc section -- start
public class Example3 {
  public String testMethod1() {
    final String simpleScript1Violate =
            """
            this is simple test""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    final String simpleScript1Correct =
            """
            this is simple test
            """;

    String simpleScript2Violate = simpleScript1Correct
        +
        """
        very good""".charAt(0);
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    String simpleScript2Correct = simpleScript1Correct
        +
        """
        very good
        """.charAt(0);

    getData(
        """
        This is a multi-line message.""");
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    getData(
        """
        This is a multi-line message.
        """);

    return
        """
        THE MULTI-LINE MESSAGE""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
  }
  public void getData(String text, int num) {}
  public void getData(String text) {}
}
// xdoc section -- end
