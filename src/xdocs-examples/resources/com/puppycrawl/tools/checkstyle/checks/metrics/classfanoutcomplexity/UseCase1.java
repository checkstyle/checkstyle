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

// Fan-out count: Time, Place, BufferedReader, File
// Total = 4
// xdoc section -- start
class UseCase1 { // violation 'Class Fan-Out Complexity is 4 (max allowed is 3)'
  Set set = new HashSet();   // ok, Set and HashSet are ignored
  Map map = new HashMap();   // ok, Map and HashMap are ignored
  Date date = new Date();    // ok, java.util package is ignored
  Time time = new Time();
  Place place = new Place();
  int value = 10;            // ok, primitive types are ignored
  BufferedReader br;
  File file;

  void method() {
    var result = "result";   // ok, var is ignored
  }
}
// xdoc section -- end
