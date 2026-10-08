/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassHeaderWrapOpenjdk"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.classheaderwrapopenjdk;

import java.util.HashMap;

// xdoc section - start
public class Example1 {
  abstract static class ValidGenericContainer<T, S>
          extends HashMap<T, S>
          implements Comparable<T> {
  }

  abstract static class InvalidGenericContainer<T, S>
          extends HashMap<T, S> implements Comparable<T> {
    // violation above """The 'implements' clause should be on a new line when
    // the class header is wrapped."""
  }

  static class UnnecessaryWrap
          extends Object {
    // violation 2 lines above """The class header should not be wrapped as it
    // fits within the maximum column limit of 80."""
  }
}
// xdoc section - end
