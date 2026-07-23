/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodParameterAlignment">
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace
    .openjdkmethodparameteralignment;

// xdoc section -- start
class Example2 {

  Example2(int a,
           int b, int c) {}

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
