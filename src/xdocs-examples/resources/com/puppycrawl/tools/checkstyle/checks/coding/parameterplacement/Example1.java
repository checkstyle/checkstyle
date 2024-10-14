/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterPlacement">
    <property name="option" value="own_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

// xdoc section -- start
class Example1
{
  // violation below 'Parameters must be placed on separate lines.'
  Example1(int a) {
  }

  Example1(
      char a) {
  }

  // violation below 'Parameters must be placed on separate lines.'
  Example1(int a, int b) {
  }

  // violation below 'Parameters must be placed on separate lines.'
  Example1(char a,
      int b) {
  }

  Example1(
      byte a,
      // violation below 'Parameters must be placed on separate lines.'
      int b, int c,
      int d) {
  }

  Example1(
      float a,
      int b,
      int c) {
  }

}
// xdoc section -- end
