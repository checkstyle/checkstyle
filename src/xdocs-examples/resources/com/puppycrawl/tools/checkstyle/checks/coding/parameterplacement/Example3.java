/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterPlacement">
    <property name="option" value="separate_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

// xdoc section -- start
class Example3
{
  Example3() {
  }

  Example3(int a) {
  }

  Example3(
          char a) {
  }

  // violation below 'Parameters must be placed on separate lines.'
  Example3(int a, int b) {
  }

  Example3(char a,
          int b) {
  }

  Example3(
          byte a,
          // violation below 'Parameters must be placed on separate lines.'
          int b, int c,
          int d) {
  }

  Example3(
          float a,
          int b,
          int c) {

  }

}
// xdoc section -- end
