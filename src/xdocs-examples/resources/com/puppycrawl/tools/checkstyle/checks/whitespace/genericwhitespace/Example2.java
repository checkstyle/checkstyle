/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// xdoc section -- start
public class Example2 {
  List< String> l; // violation, ''<' is followed by whitespace.'
  Box b = Box. <String>of("foo"); // violation, ''<' is preceded with whitespace.'
  public<T> void foo() {} // violation, ''<' is not preceded with whitespace.'

  List a = new ArrayList<> (); // violation, ''>' is followed by whitespace.'
  Map<Integer, String>m; // violation, ''>' is not followed by whitespace.'
  Pair<Integer, Integer > p; // violation, ''>' is preceded with whitespace.'
}
// xdoc section -- end

class Box {
    public static <T> Box of(T value) {
        // Implementation details
        return new Box();
    }
}

class Pair<T1, T2> {
    // Implementation details
}