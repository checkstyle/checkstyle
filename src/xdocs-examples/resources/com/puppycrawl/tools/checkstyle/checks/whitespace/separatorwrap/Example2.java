/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SeparatorWrap">
      <property name="option" value="nl"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;
// xdoc section -- start
import java.io.
        IOException; // violation above '.' should be on a new line

class Example2 {
  String s;

  int a,
          b;         // violation above ',' should be on a new line

  void foo(int x,
           int y) {  // violation above ',' should be on a new line
  }

  void bar(int p
          , int q) {      // ok, ',' is on a new line
    if (s
            .isEmpty()) { // ok, '.' is on a new line
    }
  }
}
// xdoc section -- end
