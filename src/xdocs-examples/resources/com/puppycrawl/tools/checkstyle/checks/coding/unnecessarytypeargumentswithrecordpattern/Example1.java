/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryTypeArgumentsWithRecordPattern"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarytypeargumentswithrecordpattern;

record Box<T>(T t) {}
record Pair<A, B>(A a, B b) {}

// xdoc section -- start
public class Example1 {
  private Box<String> box = null;
  private Box<Box<String>> nested = null;
  private Pair<String, Integer> pair = null;

  public void test() {
    // violation below 'Unnecessary type arguments with record pattern.'
    if (box instanceof Box<String>(var s)) {}
    if (box instanceof Box(var s)) {}

    // violation below 'Unnecessary type arguments with record pattern.'
    if (pair instanceof Pair<String, Integer>(var a, var b)) {}
    if (pair instanceof Pair(var a, var b)) {}

    // 2 violations 3 lines below:
    //                        'Unnecessary type arguments with record pattern.'
    //                        'Unnecessary type arguments with record pattern.'
    if (nested instanceof Box<Box<String>>(Box<String>(var s))) {}
    if (nested instanceof Box(Box(var s))) {}

    switch (nested) {
      // violation below 'Unnecessary type arguments with record pattern.'
      case Box<Box<String>>(Box(var s)) -> {}
      default -> {}
    }
    switch (nested) {
      case Box(Box(var s)) -> {}
      default -> {}
    }
  }
}
// xdoc section -- end
