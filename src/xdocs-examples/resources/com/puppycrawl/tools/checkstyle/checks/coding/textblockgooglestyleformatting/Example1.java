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

  public String textFun() {

    final String simpleScript =
        """
        s
        """;

    final String simpleScript1 =
        """
        this is simple test;
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
    return "this is function";
  }
}
// xdoc section -- end
