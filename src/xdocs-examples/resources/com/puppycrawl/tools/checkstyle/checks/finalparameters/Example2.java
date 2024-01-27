/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="tokens" value="CTOR_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example2 {
  public Example2() { }
  public Example2(final int m) { }
  public Example2(final int m,int n) { } // violation, 'n should be final'
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { }
}
// xdoc section -- end
