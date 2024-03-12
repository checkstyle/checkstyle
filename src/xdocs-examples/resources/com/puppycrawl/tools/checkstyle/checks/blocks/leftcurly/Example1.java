/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

// xdoc section -- start
class Example1
{ // violation, ''{' at column 1 should be on the previous line.'
  private interface TestInterface
  { // violation, ''{' at column 3 should be on the previous line.'
  }

  private
    class
    MyClass { // OK
  }

  enum Colors {RED, // OK
    BLUE,
    GREEN;
  }
}
// xdoc section -- end
