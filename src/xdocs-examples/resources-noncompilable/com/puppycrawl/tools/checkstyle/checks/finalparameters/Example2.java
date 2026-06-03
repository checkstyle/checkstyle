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
  public Example2(final int m, int n) { } // violation, 'n should be final'
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { }

  void testCatchParameters() {
    try { int x = 1 / 0; }
    catch (Exception e) {
      System.out.println(e);
    }
    try { int x = 1 / 0; }
    catch (Exception _) {
      System.out.println("infinity");
    }
    try { int x = 1 / 0; }
    catch (final Exception _) {
      System.out.println("infinity");
    }
  }

  void testForEachParameters() {
    int[] l = {1, 2, 3};
    int x = 0;
    for (int number: l) {
      System.out.println(number);
    }
    for (int _: l) { x++; }
  }
}
// xdoc section -- end
