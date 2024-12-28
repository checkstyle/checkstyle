/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="excludedPackages" value="java.util"/>
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
class Example6{ // violation 'Class Fan-Out Complexity is 4 (max allowed is 3)'
  Set set = new HashSet(); // Set, HashSet ignored
  Map map = new HashMap(); // Map, HashMap ignored
  Date date = new Date(); // Ignored
  Time6 time = new Time6(); // Counted, 1
  Place6 place = new Place6(); // Counted, 2
  int value = 10; // int is ignored
  BufferedReader br; // Counted, 3
  File file; // Counted, 4
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place6 {}
class Time6 {}
// xdoc section -- end
