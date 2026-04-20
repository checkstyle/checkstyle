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

    final String firstViolation =
        """
        too little indentation
        """; // violation above
    final String firstCorrect =
        """
        proper indentation
        """;

    consume(
        """
        too little indentation
        """); // violation above
    consume(
        """
        proper indentation
        """);

    return firstViolation + firstCorrect;
  }

  public void consume(String text) {}
}
// xdoc section -- end
