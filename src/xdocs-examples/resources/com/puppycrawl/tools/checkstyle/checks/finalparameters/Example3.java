/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="ignorePrimitiveTypes" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example3 {
  public Example3() { }
  public Example3(final int m) { }
  public Example3(final int m, int n) { }
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { } // violation, 'args should be final'
}
// xdoc section -- end
