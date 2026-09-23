/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodParameterAlignment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace
    .openjdkmethodparameteralignment;

// xdoc section - start
class Example1 {

  // violation below 'Only one parameter is allowed per line in a vertical list.'
  Example1(int a,
           int b, int c) {}

  void oneParameterPerLine(int a,
                           int b,
                           int c) {}

  // violation below 'Only one parameter is allowed per line in a vertical list.'
  void twoOnLastLine(int a,
                     int b, int c) {}

  // violation below 'Only one parameter is allowed per line in a vertical list.'
  void twoOnFirstLine(int a, int b,
                      int c) {}

  // violation below 'Align parameters vertically or wrap with eight extra spaces.'
  void notAligned(int a,
      int b) {}

  void wrappedByEightSpaces(int a, int b,
          int c) {}
}
// xdoc section - end
