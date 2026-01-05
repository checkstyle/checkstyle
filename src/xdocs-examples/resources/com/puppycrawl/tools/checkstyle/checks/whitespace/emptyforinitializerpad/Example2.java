/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForInitializerPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

// xdoc section -- start
class Example2 {
  int i = 0;
  void example() {
    for ( ; i < 1; i++ );
    for (; i < 2; i++ );    // violation '';' is not preceded with whitespace'
    for (;i<2;i++);         // violation '';' is not preceded with whitespace'
    for ( ;i<2;i++);
    for (
          ; i < 2; i++ );
  }
}
// xdoc section -- end
