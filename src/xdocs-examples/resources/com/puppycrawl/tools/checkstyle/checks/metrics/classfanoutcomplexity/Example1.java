/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassFanOutComplexity"/>
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
class Example1{
  Set set = new HashSet(); // Set, HashSet ignored
  Map map = new HashMap(); // Map, HashMap ignored
  Date date = new Date(); // Counted, 1
  Time1 time = new Time1(); // Counted, 2
  Place1 place = new Place1(); // Counted, 3
  int value = 10; // int is ignored
  BufferedReader br;
  File file;
  void method() {
    var result = "result"; // var is ignored
  }
}
class Place1 {}
class Time1 {}
// xdoc section -- end
