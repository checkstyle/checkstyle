/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodParameterAlignment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace
    .openjdkmethodparameteralignment;

// xdoc section -- start
class Example1 {

  Example1(int a,
           int b, int c) {} // violation 'Only one parameter is allowed'

  void oneParameterPerLine(int a,
                           int b,
                           int c) {}

  void twoOnLastLine(int a,
                     int b, int c) {} // violation 'Only one parameter is allowed'

  void twoOnFirstLine(int a, int b, // violation 'Only one parameter is allowed'
                      int c) {}

  void wrappedByEightSpaces(int a, int b,
          int c) {}
}
// xdoc section -- end
