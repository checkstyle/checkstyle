/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoWhitespaceAfter">
      <property name="allowLineBreaks" value="false"/>
      <property name="tokens" value="DOT"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

// xdoc section -- start
class Example2 {
  public void lineBreak(String x) {
    Integer.
        parseInt(x); // violation above ''.' is followed by whitespace'
    Integer.parseInt(x);
  }

  public void dotOperator(String s) {
    Integer.parseInt(s);
    Integer. parseInt(s); // violation ''.' is followed by whitespace'
  }

  public void arrayDec() {
    int[] arr;
    int [] array;
  }

  public void bitwiseNot(int a) {
    a = ~ a;
    a = ~a;
  }
}
// xdoc section -- end
