/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TextBlockGoogleStyleFormatting"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// xdoc section -- start
public class Example4 {

  public String testFun() {

    final String simpleScript =
    """                
          s
           """; // violation 'text-block quotes are not vertically aligned'

    getData(
    1,
        """
        this is a multi-line message
        """); // violation 'Text-block quotes are not vertically aligned'

    return
        """
    this is sample text
    """; // violation 'text-block quotes are not vertically aligned'
  }
  public void getData(int num, String text) {}
}
// xdoc section -- end
