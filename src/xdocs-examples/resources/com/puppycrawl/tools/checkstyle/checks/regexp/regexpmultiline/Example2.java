/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="System\.(out)|(err)\.print(ln)?\("/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example2 {
  void testMethod1() {
    System.out.print("Example"); // violation
    System.err.println("Example"); // violation
    System
      .out.print("Example");
    System
      .err.println("Example"); // violation
    System.
      out.print("Example");
    System.
      err.println("Example"); // violation
  }

  void testMethod2() {
    System.out.println("Test #1: this is a test string"); // violation
    System.out.println("TeSt #2: This is a test string"); // violation
    System.out.println("TEST #3: This is a test string"); // violation
    int i = 5;
    System.out.println("Value of i: " + i); // violation
    System.out.println("Test #4: This is a test string"); // violation
    System.out.println("TEst #5: This is a test string"); // violation
  }
}
// xdoc section -- end
