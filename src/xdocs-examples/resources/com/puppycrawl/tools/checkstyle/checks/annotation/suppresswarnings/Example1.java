/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarnings"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

// xdoc section -- start
// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
class Example1 {
  // violation below 'The warning '' cannot be suppressed at this location'
  @SuppressWarnings("")
  final int num1 = 1;
  // ok below as default format only checks for a blank or empty string
  @SuppressWarnings("all")
  final int num2 = 2;
  // ok below as default format only checks for a blank or empty string
  @SuppressWarnings("unused")
  final int num3 = 3;

  void foo1(@SuppressWarnings("unused") int param) {}

  @SuppressWarnings("all")
  void foo2(int param) {}
  @SuppressWarnings("unused")
  void foo3(int param) {}
  @SuppressWarnings(true?"all":"unused")
  void foo4(int param) {}
}
// ok below as default format only checks for a blank or empty string
@SuppressWarnings("unchecked")
class Test1 {}
// xdoc section -- end
