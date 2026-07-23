/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="tokens" value="CTOR_DEF"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example2 {
  public Example2() { }
  public Example2(final int m) { }
  public Example2(final int m, int n) { } // violation 'n should be final'
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { }

  void testCatchParameters() {
    try { } catch (Exception e) { }
    try { } catch (Exception _) { }
    try { } catch (final Exception _) { }
  }

  void testForEachParameters() {
    for (int number: new int[] {1, 2, 3}) { }
    for (int _: new int[] {1, 2, 3}) { }
  }
}
// xdoc section -- end
