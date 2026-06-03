/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="ignorePrimitiveTypes" value="true"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example3 {
  public Example3() { }
  public Example3(final int m) { }
  public Example3(final int m, int n) { }
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { } // violation, 'args should be final'

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
