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
class Example2 {
  List <String> l; // violation, "<" followed by whitespace
  public<T> void foo() {} // violation, "<" not preceded with whitespace
  List a = new ArrayList<> (); // violation, ">" followed by whitespace
  Map<Integer, String>m; // violation, ">" not followed by whitespace
  HashSet<Integer > set; // violation, ">" preceded with whitespace
  record License<T> () {} // violation, ">" followed by whitespace
}
// xdoc section -- end
