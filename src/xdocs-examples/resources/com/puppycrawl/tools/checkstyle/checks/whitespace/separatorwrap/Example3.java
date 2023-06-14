/*
SeparatorWrap
tokens = COMMA
option = nl


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

class Example3 {
  String s;

  int a,
    b; // violation above '',' should be on a new line'

  void foo(int a,
                int b) { // violation above '',' should be on a new line'
    int r
      , t; // OK
  }

  void bar(int p
                , int q) { // OK
  }
}
