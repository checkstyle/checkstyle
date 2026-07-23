/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="System\.(out|err)\.print(ln)?\("/>
    <property name="message" value="Avoid using System.out/err for printing."/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example8 {
  void testMethod1() {
    // violation below 'Avoid using System.out/err for printing.'
    System.out.print("Example");
    // violation below 'Avoid using System.out/err for printing.'
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
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("Test #1: this is a test string");
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("TeSt #2: This is a test string");
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("TEST #3: This is a test string");
    int i = 5;
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("Value of i: " + i);
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("Test #4: This is a test string");
    // violation below 'Avoid using System.out/err for printing.'
    System.out.println("TEst #5: This is a test string");
  }
}
// xdoc section -- end
