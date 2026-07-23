/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SeparatorWrap"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;
// xdoc section -- start
import java.io.
        IOException; // ok, '.' is on the previous line

class Example1 {
  String s;

  int a,
          b; // ok, ',' is on the previous line

  void foo(int x,
           int y) { // ok, ',' is on the previous line
  }

  void bar(int p
          , int q) { // violation ',' should be on the previous line
    if (s
            .isEmpty()) { // violation '.' should be on the previous line
    }
  }
}
// xdoc section -- end
