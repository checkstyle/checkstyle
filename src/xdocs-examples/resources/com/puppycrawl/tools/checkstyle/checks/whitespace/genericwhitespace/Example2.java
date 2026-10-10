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

// xdoc section - start
class Example2 {
  List <String> l; // violation ''<' is preceded with whitespace.'
  public<T> void foo() {} // violation ''<' is not preceded with whitespace.'
  List a = new ArrayList<> (); // violation ''>' is followed by whitespace.'
  Map<Integer, String>m; // violation ''>' is followed by an illegal character.'
  HashSet<Integer > set; // violation ''>' is preceded with whitespace.'
  record License<T> () {} // violation ''>' is followed by whitespace.'
}
// xdoc section - end
