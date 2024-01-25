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
class Example1 {
  List<Integer> list1 = new ArrayList<>() { // violation
    {
      add(1);
    }
  };
  List<String> list2 = new ArrayList<>() { // violation
        ;
    // comments and semicolons are ignored
    {
      add("foo");
    }
  };
  List<Object> list = new ArrayList<>() {
    // OK, as it is not pure double brace pattern
    private int field;
    {
      add(new Object());
    }
  };
}
// xdoc section -- end
