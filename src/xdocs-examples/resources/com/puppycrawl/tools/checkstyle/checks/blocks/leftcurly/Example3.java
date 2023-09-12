/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly">
      <property name="option" value="nlow"/>
      <property name="tokens" value="CLASS_DEF,INTERFACE_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

// xdoc section -- start
class Example3
{ // violation, ''{' at column 1 should be on the previous line.'
  private interface TestInterface { // OK
  }

  private
    class
    MyClass { // violation, ''{' at column 13 should be on a new line.'
  }

  enum Colors {RED, // OK
    BLUE,
    GREEN;
  }
}
// xdoc section -- end
