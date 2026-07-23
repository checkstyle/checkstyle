/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarnings">
      <property name="format"
          value="^unchecked$|^unused$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

// xdoc section -- start
// ok below, since we are only checking for '^unchecked$|^unused$'
@SuppressWarnings("")
class Example3 {
  // ok below, since we are only checking for '^unchecked$|^unused$'
  @SuppressWarnings("")
  final int num1 = 1;
  // ok below as default format only checks for a blank or empty string
  @SuppressWarnings("all")
  final int num2 = 2;
  // violation below 'The warning 'unused' cannot be suppressed at this location'
  @SuppressWarnings("unused")
  final int num3 = 3;
  // violation below 'The warning 'unused' cannot be suppressed at this location'
  void foo1(@SuppressWarnings("unused") int param) {}

  @SuppressWarnings("all")
  void foo2(int param) {}
  // violation below 'The warning 'unused' cannot be suppressed at this location'
  @SuppressWarnings("unused")
  void foo3(int param) {}
  // violation below 'The warning 'unused' cannot be suppressed at this location'
  @SuppressWarnings(true?"all":"unused")
  void foo4(int param) {}
}
// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
class Test3 {}
// xdoc section -- end
