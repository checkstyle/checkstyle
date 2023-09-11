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
  // ok below as The warning 'all' is a valid warning type
  @SuppressWarnings("all")
  final int num2 = 2;
  // ok below as The warning 'unused' is a valid warning type
  @SuppressWarnings("unused")
  final int num3 = 3;

  void foo1(@SuppressWarnings("unused") int param) {} // ok

  @SuppressWarnings("all") // ok
  void foo2(int param) {}
  @SuppressWarnings("unused") // ok
  void foo3(int param) {}
  @SuppressWarnings(true?"all":"unused") // ok
  void foo4(int param) {}
}
// ok below as The warning 'unchecked' is a valid warning type
@SuppressWarnings("unchecked")
class Test1 {}
// xdoc section -- end
