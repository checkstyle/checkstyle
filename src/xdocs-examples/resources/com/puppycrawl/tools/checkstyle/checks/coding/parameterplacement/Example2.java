/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterPlacement">
    <property name="option" value="own_line_allow_single_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

// xdoc section -- start
class Example2
{
  Example2() {
  }

  Example2(int a) {
  }

  Example2(
          char a) {
  }

  Example2(int a, int b) {

  }

  // violation below 'Parameters must be placed on separate lines.'
  Example2(char a,
          int b) {

  }

  Example2(
          byte a,
          // violation below 'Parameters must be placed on separate lines.'
          int b, int c,
          int d) {

  }

  Example2(
          float a,
          int b,
          int c) {

  }

}
// xdoc section -- end
