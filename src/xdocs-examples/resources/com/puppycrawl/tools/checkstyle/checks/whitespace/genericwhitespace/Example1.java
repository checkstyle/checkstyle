/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;
import com.google.common.collect.ImmutableList;
import java.util.*;

// xdoc section -- start
public class Example1 {

  @FunctionalInterface
  interface ImmutableListBuilder<T> {
    ImmutableList.Builder<T> createBuilder();
  }

  // Generic methods definitions
  public static <K, V> boolean isFoo(K key, V value) {
    return false;
  }
  public<T> void foo() {} // violation, ''<' is not preceded with whitespace.'

  // Generic type definition
  class MyClass<T1, T2> {
    public MyClass() {}
    public MyClass(T1 t1, T2 t2) {}
  }
  public void examples(){
    // Generic type declaration
    Map<String, List<Integer>> p;
    Map<Integer, String>m; // violation, ''>' is followed by an illegal character.'
    List< String> l; // violation, ''<' is followed by whitespace.'

    // Generic method call
    boolean same = Example1.<Object, Object>isFoo(null, null);
    Box b = Box. <String>of("foo"); // violation, ''<' is preceded with whitespace.'

    // Diamond operator
    final C<Integer> a1 = new C<Integer>();
    final C<Integer> a2 = new C<Integer >(); // violation, ''>' is preceded with whitespace.'

    // Method reference
    ImmutableListBuilder<String> builderFactory = ImmutableList.Builder<String>::new;
    Collections.sort(new ArrayList<String>(), Comparable::<String>compareTo);

    // Generic constructor call
    MyClass<String, String> obj = new MyClass<String, String>();
    List a = new ArrayList<>  (); // ok, until #14344
  }
}
// xdoc section -- end

class Box {
  public static <T> Box of(T value) {return new Box();}
}
class C<T> {}
