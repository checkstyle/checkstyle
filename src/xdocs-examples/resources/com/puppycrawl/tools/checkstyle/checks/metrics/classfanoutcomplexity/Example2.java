/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="max" value="2"/>
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
class Example2{ // violation 'Class Fan-Out Complexity is 5 (max allowed is 2)'
  Set set = new HashSet(); // Set, HashSet ignored
  Map map = new HashMap(); // Map, HashMap ignored
  Date date = new Date(); // Counted, 1
  Time2 time = new Time2(); // Counted, 2
  Place2 place = new Place2(); // Counted, 3
  int value = 10; // int is ignored
  BufferedReader br; // Counted, 4
  File file; // Counted, 5
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place2 {}
class Time2 {}
// xdoc section -- end
