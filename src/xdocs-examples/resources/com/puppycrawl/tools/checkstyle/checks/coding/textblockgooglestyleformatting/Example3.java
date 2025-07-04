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
    final String simpleScript1 =
            """
            this is simple test""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    final String simpleScriptCorrect1 =
            """
            this is simple test
            """;
    String test1 = simpleScriptCorrect1
        +
        """
        very good""".charAt(0) + getName();
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    String testCorrect1 = simpleScriptCorrect1
        +
        """
        very good
        """.charAt(0) + getName();

    getData(
        """
        Hello,
        This is a multi-line message.""");
    // 2 violations above:
    //   'Closing quotes (""") of text-block must be on the new line.'
    //   'Text-block quotes are not vertically aligned'
    getData(
        """
        Hello,
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
  public String getName() {
    return null;
  }
}
// xdoc section -- end
