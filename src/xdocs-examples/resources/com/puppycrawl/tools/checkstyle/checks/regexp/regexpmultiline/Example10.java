/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="(System\s*\.\s*)(out)\s*\.\s*print\("/>
    <property name="matchAcrossLines" value="true"/>
    <property name="reportGroup" value="2"/>
    <property name="message" value="Avoid using System.out for printing."/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section - start
class Example10 {
  void testMethod1() {
    // violation below 'Avoid using System.out for printing.'
    System.out.print("Example");

    System.err.println("Example");
    System
    .out.print("Example"); // violation 'Avoid using System.out for printing.'
    System
    .err.println("Example");
    System.
    out.print("Example"); // violation 'Avoid using System.out for printing.'
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
// xdoc section - end

