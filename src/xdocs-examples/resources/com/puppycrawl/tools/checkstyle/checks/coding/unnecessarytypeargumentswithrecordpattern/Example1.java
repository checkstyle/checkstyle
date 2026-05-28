/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryTypeArgumentsWithRecordPattern"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarytypeargumentswithrecordpattern;

// xdoc section -- start
public class Example1 {
  public void test() {
    record Box<T>(T t) {}
    record Pair<A, B>(A a, B b) {}
    Box<String> box = null;
    Box<Box<String>> nested = null;
    Pair<String, Integer> pair = null;
    // violation below, 'Unnecessary type arguments with record pattern.'
    if (box instanceof Box<String>(var s)) {
      System.out.println(s);
    }
    // 2 violations 3 lines below:
    //                        'Unnecessary type arguments with record pattern.'
    //                        'Unnecessary type arguments with record pattern.'
    if (nested instanceof Box<Box<String>>(Box<String>(var s))) {
      System.out.println(s);
    }
    // violation below, 'Unnecessary type arguments with record pattern.'
    if (pair instanceof Pair<String, Integer>(var a, var b)) {
      System.out.println(a + " " + b);
    }
    switch (nested) {
      // violation below, 'Unnecessary type arguments with record pattern.'
      case Box<Box<String>>(Box(var s)) ->
        System.out.println(s);
      default -> {}
    }
    if (box instanceof Box(var s)) { // ok
      System.out.println(s);
    }
    if (box instanceof Box<?> (var s)) { // ok
      System.out.println(s);
    }
    switch (nested) {
      case Box(Box(var s)) -> // ok
        System.out.println(s);
      default -> {}
    }
  }
}
// xdoc section -- end
