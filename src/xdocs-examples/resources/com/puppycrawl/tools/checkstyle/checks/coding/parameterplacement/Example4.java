/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterPlacement">
    <property name="option" value="separate_line_allow_single_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

// xdoc section -- start
class Example4
{
  Example4() {
  }

  Example4(int a) {
  }

  Example4(
          char a) {
  }

  Example4(int a, int b) {
  }

  Example4(char a,
          int b) {
  }

  Example4(
          byte a,
          // violation below 'Parameters must be placed on separate lines.'
          int b, int c,
          int d) {
  }

  Example4(
          float a,
          int b,
          int c) {
  }

}
// xdoc section -- end
