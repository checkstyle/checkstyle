/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="tokens" value="FOR_EACH_CLAUSE, LITERAL_CATCH"/>
      <property name="ignoreUnnamedParameters" value="true"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example4 {
  public Example4() { }
  public Example4(final int m) { }
  public Example4(final int m, int n) { }
  public void methodOne(final int x) { }
  public void methodTwo(int x) { }
  public static void main(String[] args) { }

  void testCatchParameters() {
    try { } catch (Exception e) { } // violation 'e should be final'
    try { } catch (Exception _) { } // ok, unnamed catch parameter
    try { } catch (final Exception _) { }
  }

  void testForEachParameters() {
    for (int number: new int[] {1, 2, 3}) { } // violation 'number should be final'
    for (int _: new int[] {1, 2, 3}) { }
  }
}
// xdoc section -- end
