/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForInitializerPad"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

// xdoc section -- start
class Example1 {
  int i = 0;
  void example() {
    for ( ; i < 2; i++ );  // violation '';' is preceded with whitespace'
    for (; i < 2; i++ );
    for (;i<2;i++);
    for ( ;i<2;i++);       // violation '';' is preceded with whitespace'
    for (
          ; i < 2; i++ );
  }
}
// xdoc section -- end
