/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter"/>
  <module name="TreeWalker">
    <module name="NoWhitespaceAfter"/>
    <module name="MagicNumber"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section - start
public class Example1 {
  // violation below ''5' is a magic number'
  private int MyVariable = 5;

  public void exampleMethod(int a, int b) {
    int value = 100; // violation ''100' is a magic number'

    Integer. parseInt("3"); // violation ''.' is followed by whitespace'
  }

  public void printExample() {
    int [] x; // violation ''int' is followed by whitespace'
    System.out.println(
            "example"
    );
  }
}
// xdoc section - end
