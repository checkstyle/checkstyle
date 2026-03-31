/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example1 {
  public Example1() { }
  public Example1(final int m) { }
  public Example1(final int m, int n) { } // violation, 'n should be final'
  public void methodOne(final int x) { }
  public void methodTwo(int x) { } // violation, 'x should be final'
  public static void main(String[] args) { } // violation, 'args should be final'
}
// xdoc section -- end
