/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// xdoc section -- start
public class Example1 {
  @FunctionalInterface
  interface ImmListBldr<T> {
      ImmutableList.Builder<T> createBuilder();
  }
  // Generic methods definitions
  public static <K, V> boolean foo(K key, V value) {
    return false;
  }
  public<T> void foo() {} // violation, ''<' is not preceded with whitespace.'
  // Generic type definition
  class MyClass<T1, T2> {
    public MyClass() {}
    public MyClass(T1 t1, T2 t2) {}
  }

  public void examples() {
    // Generic type reference
    Map<String, List<Integer>> p;
    Map<Integer, String>m; // violation, ''>' is followed by an illegal character.'
    List< String> l; // violation, ''<' is followed by whitespace.'
    // Generic preceded method name
    boolean same = Example1.<Object, Object>foo(null, null);
    Box b = Box. <String>of("foo"); // violation, ''<' is preceded with whitespace.'
    // Diamond operator
    MyClass<Integer, String> p1 = new MyClass<Integer, String>(1, "apple");
    MyClass<Integer, Integer > p2; // violation, ''>' is preceded with whitespace.'
    // Method reference
    ImmListBldr<String> builderFactory = ImmutableList.Builder<String>::new;
    Collections.sort(new ArrayList<String>(), Comparable::<String>compareTo);
    // Constructor call
    MyClass<String, String> obj = new MyClass<String, String>();
    List a = new ArrayList<>  (); // ok, until #14344
  }
}
// xdoc section -- end
class Box {
  public static <T> Box of(T value) {
    // Implementation details
    return new Box();
  }
}
