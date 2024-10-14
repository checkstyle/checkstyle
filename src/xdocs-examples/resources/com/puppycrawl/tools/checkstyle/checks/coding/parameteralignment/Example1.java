/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterAlignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameteralignment;

// xdoc section -- start
class Example1
{
  Example1(int a,
           long b) {
  }

  Example1(char a,
    // violation below 'Parameters must be aligned.'
    float b) {
  }

  Example1(byte a,
    // violation below 'Parameters must be aligned.'
    int b, int c, int d, int e, int f, int g,
        // violation below 'Parameters must be aligned.'
        int h) {
  }

  void myMethod5(
          float a,
          int b,
          int c) {
    method6('1', 1.0f);
    method6('1',
        // violation below 'Parameters must be aligned.'
        1.0f);
  }

  void method6(char a,
      // violation below 'Parameters must be aligned.'
      float b) {
    Example1 ipa = new Example1(1,
                                11L);

    Example1 ipa2 = new Example1(2,
        // violation below 'Parameters must be aligned.'
        22L);
  }

}
// xdoc section -- end
