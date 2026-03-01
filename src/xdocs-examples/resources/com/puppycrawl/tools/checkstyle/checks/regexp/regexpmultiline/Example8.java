/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="([A-Z]+) = ([0-9]+)"/>
    <property name="maximum" value="0"/>
    <property name="reportGroup" value="2"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example8 {
  void testMethod1() {
    int A = 5; // violation, 'Line matches the illegal pattern'
    int B = 20; // violation, 'Line matches the illegal pattern'
    int C = 99; // violation, 'Line matches the illegal pattern'
  }
}
// xdoc section -- end
