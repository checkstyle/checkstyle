/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

// xdoc section -- start
// Generic type definition
class Example1<T1, T2, Tn> {
  // Generic methods definitions
  public <K, V extends Number> boolean foo(K key, V value){
    return true;
  }
  // Generic type reference
  OrderedPair<String, Box<Integer>> p;
  // Generic preceded method name
  boolean same = Util.<Integer, String>compare(p1, p2);
  // Diamond operator
  Pair<Integer, String> p1 = new Pair<>(1, "apple");
  // Method reference
  List<T> list = ImmutableList.Builder<T>::new;
  // Constructor call
  Example1 obj = new <String>Example1();
  // Record header
  record License<T>() {}
}
// xdoc section -- end
