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

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

// xdoc section -- start
public class Example4 {

  void testCatchParameters() {
    try {
      int x = 1 / 0;
    }
    catch (Exception e) { // violation, 'Parameter e should be final'
      System.out.println(e);
    }
    try {
      int x = 1 / 0;
    }
    catch (Exception _) { // ok, unnamed catch parameter, it is implicitly final
      System.out.println("infinity");
    }
    try {
      int x = 1 / 0;
    }
    // it is ok to have unnamed final parameters
    // but it is unnecessary as it is implicitly final
    catch (final Exception _) {
      System.out.println("infinity");
    }
  }

  void testForEachParameters() {
    int[] l = {1, 2, 3};
    int x = 0;
    for (int number: l) { // violation, 'Parameter number should be final'
      System.out.println(number);
    }
    // ok, unnamed enhanced for loop variable, it is implicitly final
    for (int _: l) {
      x++;
    }
  }

}
// xdoc section -- end
