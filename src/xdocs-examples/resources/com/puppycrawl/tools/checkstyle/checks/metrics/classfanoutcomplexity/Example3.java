/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity">
      <property name="excludedClasses" value="HashMap, HashSet, Place"/>
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

// Fan-out count: Set, HashSet, Map, HashMap, Date, Time, Place
// Total = 7
// xdoc section -- start
class Example3 { // violation 'Class Fan-Out Complexity is 7 (max allowed is 3)'
  Set set = new HashSet();
  Map map = new HashMap();
  Date date = new Date();
  Time time = new Time();
  Place place = new Place();
  int value = 10;            // ok, int is ignored
  BufferedReader br;
  File file;

  void method() {
    var result = "result";   // ok, var ignored
  }
}
// xdoc section -- end
