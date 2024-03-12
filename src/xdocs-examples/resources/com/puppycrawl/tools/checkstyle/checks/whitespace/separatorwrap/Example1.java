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
          IOException; // OK, dot is on the previous line

class Example1 {
  String s;

  public void foo(int a,
                    int b) { // OK, comma is on the previous line
  }

  public void bar(int p
                    , int q) { // violation '',' should be on the previous line'
    if (s
          .isEmpty()) { // violation ''.' should be on the previous line'
    }
  }
}
// xdoc section -- end
