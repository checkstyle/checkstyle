/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidDoubleBraceInitialization"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoiddoublebraceinitialization;

import java.util.ArrayList;

import java.util.List;

// xdoc section -- start
class Example2 {
  List<Object> list = new ArrayList<>() { // OK, not pure double brace pattern
    private int field;
    {
      add(new Object());
    }
  };
}
// xdoc section -- end
