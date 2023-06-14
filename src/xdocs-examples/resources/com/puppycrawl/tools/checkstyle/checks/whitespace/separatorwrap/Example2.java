/*
SeparatorWrap
tokens = METHOD_REF
option = nl


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;
// xdoc section -- start
import java.util.Arrays;

class Example2 {
  String[] stringArray = {"foo", "bar"};

  void fun() {
    // violation below ''::' should be on a new line'
    Arrays.sort(stringArray, String::
      compareToIgnoreCase);
    Arrays.sort(stringArray, String
      ::compareTo); // OK, because it is on a new line
  }
}
// xdoc section -- end
