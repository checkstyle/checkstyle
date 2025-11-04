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

  public String textFun() {

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    final String simpleScript = """
        s
      """; // violation 'Text-block quotes are not vertically aligned'

    // violation 2 lines below:
    //   'Opening quotes (""") of text-block must be on the new line'
    final String simpleScript2 = simpleScript +
        simpleScript + """
        this is simple script
        """; // violation 'Text-block quotes are not vertically aligned'

    // violation 2 lines below:
    //   'Opening quotes (""") of text-block must be on the new line'
    getData(
        1, """
           this is a multi-line message
           """
    );

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    return """
        this is sample text
        """; // violation 'Text-block quotes are not vertically aligned'
  }

  public void getData(String text, int num) {}

  public void getData(int num, String text) {}

  public String getName() {
    return "this is function";
  }
}
// xdoc section -- end
