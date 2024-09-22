/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.time.Instant;
import java.time.LocalTime;

// xdoc section -- start
public class Example3 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default
  Instant instant = Instant.now(); // Counted 1
  LocalTime localTime = LocalTime.now(); // Counted 2
}
// xdoc section -- end
