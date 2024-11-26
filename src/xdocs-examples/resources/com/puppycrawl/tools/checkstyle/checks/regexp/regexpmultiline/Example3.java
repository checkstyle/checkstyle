/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="matchAcrossLines" value="true"/>
    <property name="format" value="System\.out.*?print\("/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example3 {
  void testMethod1() {
    // violation below, 'Line matches the illegal pattern'
    System.out.print("Example");

    System.err.println("Example");
    System
    .out.print("Example");
    System
    .err.println("Example");
    System.
    out.print("Example");
    System.
    err.println("Example");
  }

  void testMethod2() {

    System.out.println("Test #1: this is a test string");

    System.out.println("TeSt #2: This is a test string");

    System.out.println("TEST #3: This is a test string");
    int i = 5;

    System.out.println("Value of i: " + i);

    System.out.println("Test #4: This is a test string");

    System.out.println("TEst #5: This is a test string");
  }
}
// xdoc section -- end
