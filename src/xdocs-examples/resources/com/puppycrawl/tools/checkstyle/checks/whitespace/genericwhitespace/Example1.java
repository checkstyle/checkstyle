/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.*;

// xdoc section -- start
class Example1 {
  List<String> l;
  public <T> void foo() {}
  List a = new ArrayList<>();
  Map<Integer, String> m;
  HashSet<Integer> set;
  record License<T>() {}
}
// xdoc section -- end
