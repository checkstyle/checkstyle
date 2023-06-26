/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SeparatorWrap">
      <property name="tokens" value="COMMA"/>
      <property name="option" value="nl"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;
// xdoc section -- start
class Example3 {
  String s;

  int a,
    b; // violation above '',' should be on a new line'

  void foo(int a,
                int b) { // violation above '',' should be on a new line'
    int r
      , t; // OK, because it is on a new line
  }

  void bar(int p
                , int q) { // OK, because it is on a new line
  }
}
// xdoc section -- end
