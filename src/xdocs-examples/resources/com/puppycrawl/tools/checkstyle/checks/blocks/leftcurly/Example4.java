/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly">
      <property name="ignoreEnums" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

// xdoc section -- start
class Example4
{ // violation, ''{' at column 1 should be on the previous line.'
  private interface TestInterface
  { // violation, ''{' at column 3 should be on the previous line.'
  }

  private
    class
    MyClass { // OK
  }

  enum Colors {RED, // violation, ''{' at column 15 should have line break after.'
    BLUE,
    GREEN;
  }
}
// xdoc section -- end
