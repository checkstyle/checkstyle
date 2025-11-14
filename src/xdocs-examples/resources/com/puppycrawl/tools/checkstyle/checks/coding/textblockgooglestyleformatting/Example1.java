/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// xdoc section -- start
public class Example1 {

  public String testMethod1() {

    final String simpleScript =
"""
        contents of Text block
""";

    final String simpleScript1 =
        """
        contents of Text block
        """;

    String stringFollowedBy =
        """
          Hello there
        """ + getName();

    getData(
        """
        Hello,
        This is a multi-line message.
        """, 0);

    return
        """
        this is sample text
        """;
  }

  public void getData(String text, int num) {}

  public String getName() {
    return null;
  }
}
// xdoc section -- end
