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

// Fan-out count: Date, Time, Place
// Total = 3
// xdoc section -- start
class Example5 {
  Set set = new HashSet();   // ok, Set and HashSet are ignored
  Map map = new HashMap();   // ok, Map and HashMap are ignored
  Date date = new Date();
  Time time = new Time();
  Place place = new Place();
  int value = 10;            // ok, primitive types are ignored
  BufferedReader br;         // ok, BufferedReader is ignored
  File file;                 // ok, File is ignored

  void method() {
    var result = "result";   // ok, var is ignored
  }
}
// xdoc section -- end
