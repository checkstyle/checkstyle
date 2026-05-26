/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="Test #[0-9]+: ([A-Za-z ]+)"/>
    <property name="maximum" value="0"/>
    <property name="reportGroup" value="1"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example7 {
  void testMethod1() {
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #1: this is a test string");

    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #2: another test string");
  }
}
// xdoc section -- end
