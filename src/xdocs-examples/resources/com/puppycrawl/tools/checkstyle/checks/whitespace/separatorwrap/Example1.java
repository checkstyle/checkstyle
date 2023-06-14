/*
SeparatorWrap


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

import java.io.
          IOException; // OK

class Example1 {
  String s;

  public void foo(int a,
                    int b) { // OK
  }

  public void bar(int p
                    , int q) { // violation '',' should be on the previous line'
    if (s
          .isEmpty()) { // violation ''.' should be on the previous line'
    }
  }
}
