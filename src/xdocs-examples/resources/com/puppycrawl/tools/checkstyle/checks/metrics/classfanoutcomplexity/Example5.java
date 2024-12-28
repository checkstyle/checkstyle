/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="excludedPackages" value="java.io"/>
      <property name="max" value="5"/>
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
class Example5{
  Set set = new HashSet(); // Set, HashSet ignored
  Map map = new HashMap(); // Map, HashMap ignored
  Date date = new Date(); // Counted, 1
  Time5 time = new Time5(); // Counted, 2
  Place5 place = new Place5(); // Counted, 3
  int value = 10; // int is ignored
  BufferedReader br; // Ignored
  File file; // Ignored
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place5 {}
class Time5 {}
// xdoc section -- end
