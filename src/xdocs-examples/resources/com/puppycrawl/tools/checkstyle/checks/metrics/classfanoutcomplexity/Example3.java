/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="excludedClasses" value="HashMap, HashSet, Place3"/>
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
class Example3{ // violation 'Class Fan-Out Complexity is 7 (max allowed is 3)'
  Set set = new HashSet(); // Set counted 1, HashSet ignored
  Map map = new HashMap(); // Map counted 2, HashMap ignored
  Date date = new Date(); // Counted, 3
  Time3 time = new Time3(); // Counted, 4
  Place3 place = new Place3(); // Counted, 5
  int value = 10; // int is ignored
  BufferedReader br; // Counted, 6
  File file; // Counted, 7
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place3 {}
class Time3 {}
// xdoc section -- end
