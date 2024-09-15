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
  // violation below 'Avoid double brace initialization.'
  List<Integer> list1 = new ArrayList<>() {
    {
      add(1);
    }
  };
  // violation below 'Avoid double brace initialization.'
  List<String> list2 = new ArrayList<>() {
        ;
    // comments and semicolons are ignored
    {
      add("foo");
    }
  };
  List<Object> list = new ArrayList<>() {
    // OK, as it is not double brace pattern
    private int field;
    {
      add(new Object());
    }
  };
}
// xdoc section -- end
