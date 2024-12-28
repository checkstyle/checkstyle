/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="excludeClassesRegexps" value=".*Reader$"/>
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.io.BufferedReader;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

// xdoc section -- start
class Example4{ // violation 'Class Fan-Out Complexity is 4 (max allowed is 3)'
  Set set = new HashSet(); // Set, HashSet ignored
  Map map = new HashMap(); // Map, HashMap ignored
  Date date = new Date(); // Counted, 1
  Time4 time = new Time4(); // Counted, 2
  Place4 place = new Place4(); // Counted, 3
  int value = 10; // int is ignored
  BufferedReader br; // Ignored
  File file; // Counted, 4
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place4 {}
class Time4 {}
// xdoc section -- end
