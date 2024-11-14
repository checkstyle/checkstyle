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
class Example1 {
  List<String> l;
  Box b = Box.<String>of("foo");
  public <T> void foo() {}
  List a = new ArrayList<>();
  Map<Integer, String> m;
  Pair<Integer, Integer> p;
  record License<T>() {}
}
// xdoc section -- end
